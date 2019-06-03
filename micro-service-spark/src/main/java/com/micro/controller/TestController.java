package com.micro.controller;

import com.micro.service.ElasticSearchReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.controller
 * Author:   hhc
 * Date:     2019/6/3
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@RestController
public class TestController {

    @Autowired
    ElasticSearchReader elasticSearchReader;

    @GetMapping("/test")
    String test(@RequestParam String id){
        return id;
    }

    @GetMapping("/getTopN")
    List<String> getTopN(@RequestParam String col,@RequestParam Integer num){
        Dataset<Row> dataset= elasticSearchReader.getDataset().select(col);
        List<String> list= (List<String>) dataset.take(num);
        return list;
    }

}
