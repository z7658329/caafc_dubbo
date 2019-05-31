package com.micro.config;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.config
 * Author:   utils
 * Date:     2018/6/29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ElasticConfig implements FactoryBean<RestHighLevelClient>, InitializingBean, DisposableBean {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private RestHighLevelClient restHighLevelClient;

    @Value("${spring.data.elasticsearch.cluster-high-nodes}")
    private String clusterNodes;


    /**
     * 控制Bean的实例化过程
     * @return
     * @throws Exception
     */
    @Override
    public RestHighLevelClient getObject() throws Exception {
        return restHighLevelClient;
    }
    /**
     * 获取接口返回的实例的class
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Override
    public void destroy() throws Exception {
        try {
            if(null != restHighLevelClient){
                restHighLevelClient.close();
            }
        } catch (final Exception e) {
            logger.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        restHighLevelClient = buildClient();
    }

    private RestHighLevelClient buildClient() {
        try {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(
                                    clusterNodes.split(":")[0],
                                    Integer.parseInt(clusterNodes.split(":")[1]),
                                    "http")));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return restHighLevelClient;
    }
}