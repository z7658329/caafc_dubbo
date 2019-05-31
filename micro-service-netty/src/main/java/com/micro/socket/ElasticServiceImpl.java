package com.micro.socket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.service.impl
 * Author:   utils
 * Date:     2018/6/29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Component
public class ElasticServiceImpl implements ElasticService {
    Logger logger=LoggerFactory.getLogger(getClass());

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public String get(String index, String type, String id) {
        GetRequest getRequest=new GetRequest(index,type,id);
         try {
             GetResponse getResponse =restHighLevelClient.get(getRequest);
            return getResponse.toString();
        } catch (IOException e) {
            logger.info(e.toString());
            return null;
        }
    }
    @Override
    public String insert(String index, String type, String id, String jsonDocument) {
        IndexRequest indexRequest = createIndexRequest(index, type, id,jsonDocument);
        try {
            IndexResponse indexResponse=restHighLevelClient.index(indexRequest);
            return indexResponse.toString();
        } catch (IOException e) {
            logger.info(e.toString());
            return e.toString();
        }

    }
    @Override
    public String insertBulk(String index, String type,String idName, String documents) {
        BulkRequest bulkRequest = new BulkRequest();
        JSONArray jsonDocuments= (JSONArray) JSONArray.parse(documents);
        for(int i=0;i<jsonDocuments.size();i++){
            JSONObject json=(JSONObject)jsonDocuments.get(i);
            bulkRequest.add(createIndexRequest(index, type, json.getString(idName), json.toString()));
        }
        try {
            BulkResponse bulkResponse=restHighLevelClient.bulk(bulkRequest);
            return bulkResponse.toString();
        } catch (IOException e) {
            logger.info(e.toString());
            return e.toString();
        }

    }

    @Override
    public String update(String index, String type, String id, String jsonDocument) {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc(jsonDocument,XContentType.JSON);
        try {
            UpdateResponse updateResponse=restHighLevelClient.update(updateRequest);
            return updateResponse.toString();
        } catch (IOException e) {
            logger.info(e.toString());
            return e.toString();
        }

    }

    @Override
    public String delete(String index, String type, String id) {
        DeleteRequest deleteRequest;
        if(type==null){
            deleteRequest=new DeleteRequest();
        }else if(id==null){
            deleteRequest=new DeleteRequest(index);
            deleteRequest.type(type);
        }else{
            deleteRequest=new DeleteRequest(index,type,id);
        }
        try {
            DeleteResponse deleteResponse =restHighLevelClient.delete(deleteRequest);
            return deleteResponse .toString();
        } catch (IOException e) {
            logger.info(e.toString());
            return e.toString();
        }
    }


    public  String pageQueryRequest(String keyword1, String keyword2, String startDate, String endDate,
                                                   int start, int size) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 结果开始处
        sourceBuilder.from(start);
        // 查询结果终止处
        sourceBuilder.size(size);
        // 查询的等待时间
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        MatchQueryBuilder matchbuilder;
        matchbuilder = QueryBuilders.matchQuery("message", keyword1 + " " + keyword2);
        // 同时满足两个关键字
        matchbuilder.operator(Operator.AND);
        // 查询在时间区间范围内的结果
        RangeQueryBuilder rangbuilder = QueryBuilders.rangeQuery("date");
        if (!"".equals(startDate)) {
            rangbuilder.gte(startDate);
        }
        AggregationBuilder aggregationBuilder=AggregationBuilders.dateHistogram("@T").dateHistogramInterval(DateHistogramInterval.MONTH);
        if (!"".equals(endDate)) {
            rangbuilder.lte(endDate);
            // 等同于bool，将两个查询合并
            BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
            boolBuilder.must(matchbuilder);
            boolBuilder.must(rangbuilder);
            // 排序
            FieldSortBuilder fsb = SortBuilders.fieldSort("date");
            fsb.order(SortOrder.DESC);
            sourceBuilder.sort(fsb);
            sourceBuilder.query(boolBuilder);
            sourceBuilder.aggregation(aggregationBuilder);
            SearchRequest searchRequest = new SearchRequest("request");
            searchRequest.types("doc");
            searchRequest.source(sourceBuilder);
            SearchResponse response = null;
            try {
                response = restHighLevelClient.search(searchRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.toString();
        }
        return null;
    }


    private IndexRequest createIndexRequest(String index, String type, String id, String jsondocument){
        IndexRequest indexRequest ;
        if (id == null) {
            indexRequest = new IndexRequest(index, type);
        } else {
            indexRequest = new IndexRequest(index, type, id);
        }
        indexRequest.source(jsondocument, XContentType.JSON);
        return indexRequest;
    }
}
