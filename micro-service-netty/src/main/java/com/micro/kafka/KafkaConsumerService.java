package com.micro.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.kafka
 * Author:   hhc
 * Date:     2019/5/31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

@Slf4j
@Component
public class KafkaConsumerService {

    @KafkaListener(topics = "#{kafkaConfig.topicNames}", groupId = "#{kafkaConfig.groupId}")
    public void processMessage(ConsumerRecord<Integer, String> record) {

        log.info("key{}",record.value());
        log.info("record{}",record.toString());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
