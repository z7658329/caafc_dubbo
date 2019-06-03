package com.micro.service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.service
 * Author:   hhc
 * Date:     2019/6/3
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Component
public class ElasticSearchReader {

    @Autowired
    private SparkSession sparkSession;

    private Dataset<Row> dataset;

    @PostConstruct
    public void initElasticSearch() {
        dataset = sparkSession.sqlContext().read().format("org.elasticsearch.spark.sql").option("inferSchema", "true").load("weblog*/type");
        dataset.createOrReplaceTempView("sessionTable");
        dataset.show();
    }

    public Dataset<Row> getDataset() {
        return dataset;
    }

    public void setDataset(Dataset<Row> dataset) {
        this.dataset = dataset;
    }

}
