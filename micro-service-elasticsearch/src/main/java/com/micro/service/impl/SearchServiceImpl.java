package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.elasticsearch.SearchService;
import com.micro.api.elasticsearch.model.Doc;
import com.micro.api.elasticsearch.model.DocScore;
import com.micro.api.elasticsearch.model.Institution;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.util.FastJsonUtils;
import com.micro.util.LG;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.service.impl
 * Author:   hhc
 * Date:     2019/3/20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static String [] exclude=new String[]{"content","pageContent"};

    @Override
    public PageTable<DocScore> selectDocByPage(BaseTable<Doc> doc) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.fetchSource(null,exclude);
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        Doc model=doc.getModel();

        Integer pageNum=doc.getPageNum();
        Integer pageSize=doc.getPageSize();


        //分页查询
        if(pageNum!=null&&pageSize!=null){
            sourceBuilder.from(pageNum*pageSize-pageSize);
            sourceBuilder.size(pageSize);
        }

        //时间段查询
        if(doc.getStart()!=null||doc.getEnd()!=null){
            RangeQueryBuilder rangbuilder = QueryBuilders.rangeQuery("createTime");
            rangbuilder.gte(doc.getStart());
            rangbuilder.lte(doc.getEnd());
            boolQueryBuilder.must(rangbuilder);
        }

        if(model!=null){
            //内容查询
            if(model.getContent()!=null){
                boolQueryBuilder.must(QueryBuilders.simpleQueryStringQuery(model.getContent()).analyzeWildcard(true).lenient(true).field("content").field("title").field("description").field("comments"));
            }

            //标题查询
            if(model.getTitle()!=null){
                boolQueryBuilder.must(QueryBuilders.matchQuery("title",model.getTitle()));
            }

            //描述查询
            if(model.getDescription()!=null){
                boolQueryBuilder.must(QueryBuilders.matchQuery("description",model.getDescription()));
            }

            //部门权限查询
            if(model.getDepPermission()!=null){
                boolQueryBuilder.must(QueryBuilders.termQuery("depPermission",model.getDepPermission()));
            }

            //岗位权限查询
            if(model.getPostPermission()!=null){
                boolQueryBuilder.must(QueryBuilders.termQuery("postPermission",model.getPostPermission()));
            }

            //人员权限查询
            if(model.getPersonPermission()!=null){
                boolQueryBuilder.must(QueryBuilders.termQuery("personPermission",model.getPersonPermission()));
            }
        }

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<tag1>");
        highlightBuilder.postTags("</tag1>");
        highlightBuilder.field("*");

        sourceBuilder.query(boolQueryBuilder).highlighter(highlightBuilder);

        SearchRequest searchRequest = new SearchRequest("doc").types("institution").source(sourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            log.info(LG.E(),e);
            return null;
        }

        List<DocScore> docScoreList =new ArrayList<>();
        PageTable<DocScore> pageTable=new PageTable<DocScore>();
        //结果解析
        SearchHits hit = searchResponse.getHits();
        SearchHit[] hits = hit.getHits();
        for (SearchHit searchHit : hits) {
             String sourceAsString = searchHit.getSourceAsString();
             DocScore docScore= FastJsonUtils.json2obj(sourceAsString,DocScore.class);
             docScore.setScore(searchHit.getScore());
             Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
             HighlightField highTitle = highlightFields.get("title");
             if(highTitle!=null){
                 docScore.setHTitle(highTitle.toString());
             }
             HighlightField highContent = highlightFields.get("content");
            if(highContent!=null){
                docScore.setHContent(highContent.toString());
            }
            HighlightField highDescription = highlightFields.get("description");
            if(highDescription!=null){
                docScore.setHDescription(highDescription.toString());
            }
            docScoreList.add(docScore);
        }
        pageTable.setTotalElements(hit.getTotalHits());
        pageTable.setModels(docScoreList);
        return pageTable;
    }

    @Override
    public PageTable<Institution> selectInstitutionByPage(BaseTable<Institution> institution) {
        return null;
    }
}
