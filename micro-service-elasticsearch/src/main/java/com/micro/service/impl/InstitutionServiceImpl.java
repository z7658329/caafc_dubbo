package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.elasticsearch.InstitutionService;
import com.micro.api.elasticsearch.model.Institution;
import com.micro.api.elasticsearch.model.Institution;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.dao.InstitutionRepository;
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
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Override
    public Institution insert(Institution institution) {
        return institutionRepository.save(institution);
    }

    @Override
    public Integer insertBatch(List<Institution> institutions) {
        institutionRepository.saveAll(institutions);
        return institutions.size();
    }

    @Override
    public Integer deleteById(String id) {
        institutionRepository.deleteById(id);
        return 0;
    }

    @Override
    public Integer deleteBatch(Institution institution) {
        return null;
    }

    @Override
    public Institution updateById(Institution institution) {
        return institutionRepository.save(institution);
    }

    @Override
    public Institution selectById(String id) {
        Institution institution = institutionRepository.findById(id).get();
        return institution;
    }

    @Override
    public PageTable<Institution> selectByString(BaseTable<String> baseTable) {
        Sort sort=new Sort(Sort.Direction.DESC,"pageNum");
        Pageable pageable = PageRequest.of(baseTable.getPageNum(), baseTable.getPageSize(),sort);
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(baseTable.getModel());
        Page<Institution> page = institutionRepository.search(builder,pageable);
        return TableUtil.copyTableList(page);
    }
   
}
