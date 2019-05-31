package com.micro.netty.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.mysql.TbPerDepService;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbPerDep;
import com.micro.dao.TbPerDepJpa;
import com.micro.util.TableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.netty.impl
 * Author:   hhc
 * Date:     2019/3/13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
@Slf4j
public class TbPerDepServiceImpl implements TbPerDepService {

    @Autowired
    private TbPerDepJpa tbPerDepJpa;


    @Override
    public TbPerDep insert(TbPerDep tbPerDep) {
        if(tbPerDep.getId()==null){
            tbPerDep.setId(TableUtil.uuid());
        }
        return tbPerDepJpa.saveAndFlush(tbPerDep);
    }

    @Override
    public Integer insertBatch(List<TbPerDep> tbPerDeps) {
        for(TbPerDep tbPerDep:tbPerDeps){
            insert(tbPerDep);
        }
        return tbPerDeps.size();
    }

    @Override
    public TbPerDep updateById(TbPerDep tbPerDep) {
        TbPerDep source =tbPerDepJpa.getOne(tbPerDep.getId());
        BeanUtils.copyProperties(source,tbPerDep,TableUtil.getNoNullProperties(tbPerDep));
        tbPerDepJpa.saveAndFlush(tbPerDep);
        return tbPerDep;
    }

    @Override
    public Integer updateBatch(List<TbPerDep> tbPerDeps) {
        for(TbPerDep tbPerDep:tbPerDeps){
            updateById(tbPerDep);
        }
        return tbPerDeps.size();
    }

    @Override
    public Integer deleteById(String id) {
        tbPerDepJpa.deleteById(id);
        return 1;
    }

    @Override
    public Integer deleteBatch(List<TbPerDep> tbPerDeps) {
        tbPerDepJpa.deleteAll(tbPerDeps);
        return tbPerDeps.size();
    }

    @Override
    public TbPerDep selectById(String id) {
        return tbPerDepJpa.getOne(id);
    }

    @Override
    public List<TbPerDep> selectAll(BaseTable<TbPerDep> tbPerDep) {
        String order=tbPerDep.getOrder();
        if(order==null){
            order="createTime";
        }
        if(tbPerDep.getModel()==null){
            tbPerDep.setModel(new TbPerDep());
        }
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Example<TbPerDep> example=Example.of(tbPerDep.getModel());
        return tbPerDepJpa.findAll(example,sort);
    }

    @Override
    public PageTable<TbPerDep> selectByPage(BaseTable<TbPerDep> tbPerDep) {
        Sort sort;
        if(tbPerDep.getOrder()==null){
            tbPerDep.setOrder("createTime");
        }
        if(tbPerDep.getAscDesc()==null||!tbPerDep.getAscDesc().equals("1")){
            sort = new Sort(Sort.Direction.DESC,tbPerDep.getOrder());
        }else {
            sort = new Sort(Sort.Direction.ASC,tbPerDep.getOrder());
        }
        if(tbPerDep.getPageNum()==null||tbPerDep.getPageSize()==null){
            tbPerDep.setPageNum(1);
            tbPerDep.setPageSize(8);
        }
        PageRequest pageRequest= PageRequest.of(tbPerDep.getPageNum()-1,tbPerDep.getPageSize(), sort);
        TbPerDepSpec tbPerDepSpec=new TbPerDepSpec(tbPerDep);
        Page<TbPerDep> page=tbPerDepJpa.findAll(tbPerDepSpec,pageRequest);
        return TableUtil.copyTableList(page);
    }

    private class TbPerDepSpec implements Specification<TbPerDep> {

        private BaseTable<TbPerDep> baseTable;

        TbPerDepSpec( BaseTable<TbPerDep> baseTable){
            this.baseTable=baseTable;
        }

        @Override
        public Predicate toPredicate(Root<TbPerDep> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return TableUtil.toPredicate(baseTable,root,criteriaQuery,criteriaBuilder);
        }
    }
}
