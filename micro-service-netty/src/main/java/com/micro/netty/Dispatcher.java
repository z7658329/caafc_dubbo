package com.micro.netty;


import com.micro.config.KafkaConfig;
import com.micro.kafka.KafkaProducerService;
import com.micro.socket.ElasticService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.controller
 * Author:   hhc
 * Date:     2019/5/31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

@Component
@Slf4j
@Data
public class Dispatcher implements CommandLineRunner {

    @Value("${thread.num}")
    private Integer num;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private ElasticService elasticService;
    @Autowired
    private KafkaConfig config;

    private LinkedBlockingQueue<String> queue=new LinkedBlockingQueue<>();


    @Override
    public void run(String... args) throws Exception {
        log.info("工作线程数量:{}",num);
        for(int i=0;i<num;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                   while (true){
                       String s=null;
                       try {
                           s=queue.take();
                       } catch (InterruptedException e) {
                           log.info(e.toString());
                       }
                       kafkaProducerService.sendMessage(config.getTopicNames()[0],s);
                       elasticService.insert(null,null,null,s);
                   }

                }
            }).start();
        }
    }
}
