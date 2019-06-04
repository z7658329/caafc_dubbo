package com.micro.netty;


import com.alibaba.fastjson.JSONObject;
import com.micro.config.KafkaConfig;
import com.micro.kafka.KafkaProducerService;
import com.micro.model.HttpRequest;
import com.micro.socket.ElasticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
public class DispatcherService{

    @Value("${thread.num}")
    private Integer num;
    @Autowired
    private KafkaProducerService kafkaProducerService;
    @Autowired
    private ElasticService elasticService;

    private LinkedBlockingQueue<HttpRequest> queue=new LinkedBlockingQueue<>();
    private SimpleDateFormat f = new SimpleDateFormat("yyyyMM");
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private String index="weblog";
    private String timestamp="@timestamp";
    private String type="type";


    @PostConstruct
    public void init() throws Exception {
        log.info("工作线程数量:{}",num);
        for(int i=0;i<num;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                   while(true){
                       HttpRequest httpRequest = null;
                       try {
                           httpRequest=queue.take();
                           Date now;
                           JSONObject jsonObject=JSONObject.parseObject(httpRequest.getContent());
                           if(jsonObject.containsKey(timestamp)){
                               now =new Date(jsonObject.getLong(timestamp));
                           }else {
                               now=new Date();
                           }
                           jsonObject.put(timestamp,simpleDateFormat.format(now));
                           String content=jsonObject.toJSONString();
                           kafkaProducerService.sendMessage(index,content);
                           String time=f.format(now);
                           elasticService.insert(index+time,"doc",null,content);
                       } catch (Exception e) {
                           if(null!=httpRequest){
                               log.info("Thread content:{} Exception:",httpRequest.getContent(),e);
                           }else {
                               log.info("Thread Exception:",e);
                           }
                       }
                   }
                }
            }).start();
        }
    }

    public LinkedBlockingQueue<HttpRequest> getQueue() {
        return queue;
    }

}
