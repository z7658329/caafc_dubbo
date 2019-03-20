package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.elasticsearch.DocService;
import com.micro.api.elasticsearch.model.Doc;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.dao.DocRepository;
import com.micro.util.TableUtil;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;


/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.service.impl
 * Author:   hhc
 * Date:     2019/3/6
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
public class DocServiceImpl implements DocService {

    @Autowired
    private DocRepository docRepository;

    @Override
    public Doc save(Doc doc){
        return docRepository.save(doc);
    }

    @Override
    public int delete(String id){
        docRepository.deleteById(id);
        return 0;
    }

    @Override
    public Doc update(Doc doc){
        return docRepository.save(doc);
    }

    @Override
    public Doc getOne(String id){
        Doc doc = docRepository.findById(id).get();
       return doc;
    }

    @Override
    public PageTable<Doc> searchString(BaseTable<String> baseTable){
        Sort sort=new Sort(Sort.Direction.DESC,"pageNum");
        Pageable pageable = PageRequest.of(baseTable.getPageNum(), baseTable.getPageSize(),sort);
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(baseTable.getModel());
        Page<Doc> page = docRepository.search(builder,pageable);
        return TableUtil.copyTableList(page);
    }
}
