package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.elasticsearch.DocService;
import com.micro.api.elasticsearch.model.Doc;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.dao.DocRepository;
import com.micro.util.TableUtil;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;


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
    public Doc insert(Doc doc) {
        return docRepository.save(doc);
    }

    @Override
    public Integer insertBatch(List<Doc> docs) {
        docRepository.saveAll(docs);
        return docs.size();
    }

    @Override
    public Integer deleteById(String id) {
        docRepository.deleteById(id);
        return 0;
    }

    @Override
    public Integer deleteBatch(Doc doc) {
        return null;
    }

    @Override
    public Doc updateById(Doc doc) {
        return docRepository.save(doc);
    }

    @Override
    public Doc selectById(String id) {
        Doc doc = docRepository.findById(id).get();
        return doc;
    }

    @Override
    public PageTable<Doc> selectByString(BaseTable<String> baseTable) {
        Sort sort=new Sort(Sort.Direction.DESC,"pageNum");
        Pageable pageable = PageRequest.of(baseTable.getPageNum(), baseTable.getPageSize(),sort);
        Page<Doc> page = docRepository.findAll(pageable);
        return TableUtil.copyTableList(page);
    }
}
