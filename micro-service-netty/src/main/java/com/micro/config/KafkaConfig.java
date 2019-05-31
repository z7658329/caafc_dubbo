package com.micro.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Data
public class KafkaConfig {

    @Value("${kafka.topic.groupId}")
    private String groupId;

    @Value("#{'${kafka.topic.topicNames}'.split(',')}")
    private String[] topicNames;

}