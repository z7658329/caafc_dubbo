package com.micro.config;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spark")
public class SparkContextBean {

    @Value("${spark.appName}:localeApp")
    private String appName;

    @Value("${spark.master}:locale")
    private String master;

    @Value("${spark.es.autoCreate}:true")
    private String autoCreate;

    @Value("${spark.es.nodes}:127.0.0.1")
    private String nodes;

    @Value("${spark.es.port}:9200")
    private String port;

    @Value("${spark.es.wanOnly}:true")
    private String wanOnly;

    @Bean
    @ConditionalOnMissingBean(SparkConf.class)
    public SparkConf sparkConf() throws Exception {
	   SparkConf conf = new SparkConf().setAppName(appName);
	   conf.set("es.index.auto.create",autoCreate);
	   conf.set("es.nodes",nodes);
	   conf.set("es.port",port);
	   conf.set("es.nodes.wan.only",wanOnly);
	   return conf;
    }

    @Bean
    @ConditionalOnMissingBean(JavaSparkContext.class)
    public JavaSparkContext javaSparkContext() throws Exception {
	   return new JavaSparkContext(sparkConf());
    }


    @Bean
    @ConditionalOnMissingBean(SparkSession.class)
        public SparkSession javaSparkSession() throws Exception{
        return SparkSession.builder().config(sparkConf()).getOrCreate();
    }
}