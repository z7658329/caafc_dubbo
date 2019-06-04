package com.micro.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;


@Component
@Slf4j
public class KafkaProducerService {

    @Autowired
    private  KafkaTemplate<Integer, String> kafkaTemplate;

    public void sendMessageAsyn(String topic,Integer key, String data) {
        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.send(topic,key, data);
        future.addCallback(new KafkaCallback());
    }

    public void sendMessage(String topic, String data){
        try {
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            log.info("topic:{} data:{} Exception:",topic,data,e);
        }
    }

    public void sendMessage(String topic,Integer key, String data){
        try {
            kafkaTemplate.send(topic,key, data);
        } catch (Exception e) {
            log.info(e.toString());
        }
    }
}