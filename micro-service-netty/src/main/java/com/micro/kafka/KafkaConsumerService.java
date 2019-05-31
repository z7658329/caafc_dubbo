package com.micro.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

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
public class KafkaConsumerService {

    @KafkaListener(topics = "#{kafkaConfig.topicNames}", groupId = "#{kafkaConfig.groupId}")
    public void processMessage(ConsumerRecord<Integer, String> record) {
        record.key();
        log.info(record.toString());
    }
}
