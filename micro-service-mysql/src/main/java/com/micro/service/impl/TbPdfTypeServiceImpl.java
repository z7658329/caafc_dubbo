package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.mysql.TbPdfTypeService;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbPdfType;
import com.micro.dao.TbPdfTypeJpa;
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
import java.util.Date;
import java.util.List;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.service.impl
 * Author:   hhc
 * Date:     2019/3/13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
@Slf4j
public class TbPdfTypeServiceImpl implements TbPdfTypeService {

    @Autowired
    private TbPdfTypeJpa tbPdfTypeJpa;


    @Override
    public TbPdfType insert(TbPdfType tbPdfType) {
        if(tbPdfType.getId()==null){
            tbPdfType.setId(TableUtil.uuid());
        }
        if(tbPdfType.getCreateTime()==null){
            tbPdfType.setCreateTime(new Date());
        }
        return tbPdfTypeJpa.saveAndFlush(tbPdfType);
    }

    @Override
    public Integer insertBatch(List<TbPdfType> tbPdfTypes) {
        for(TbPdfType tbPdfType:tbPdfTypes){
            insert(tbPdfType);
        }
        return tbPdfTypes.size();
    }

    @Override
    public TbPdfType updateById(TbPdfType tbPdfType) {
        if(tbPdfType.getUpdateTime()==null){
            tbPdfType.setUpdateTime(new Date());
        }
        TbPdfType source =tbPdfTypeJpa.getOne(tbPdfType.getId());
        BeanUtils.copyProperties(source,tbPdfType,TableUtil.getNoNullProperties(tbPdfType));
        tbPdfTypeJpa.saveAndFlush(tbPdfType);
        return tbPdfType;
    }

    @Override
    public Integer updateBatch(List<TbPdfType> tbPdfTypes) {
        for(TbPdfType tbPdfType:tbPdfTypes){
            updateById(tbPdfType);
        }
        return tbPdfTypes.size();
    }

    @Override
    public Integer deleteById(String id) {
        TbPdfType tbPdfType = selectById(id);
        tbPdfType.setIsDelete(1);
        tbPdfType.setUpdateTime(new Date());
        tbPdfTypeJpa.saveAndFlush(tbPdfType);
        return 1;
    }

    @Override
    public Integer deleteBatch(List<TbPdfType> tbPdfTypes) {
        tbPdfTypeJpa.deleteAll(tbPdfTypes);
        return tbPdfTypes.size();
    }

    @Override
    public TbPdfType selectById(String id) {
        return tbPdfTypeJpa.getOne(id);
    }

    @Override
    public List<TbPdfType> selectAll(BaseTable<TbPdfType> tbPdfType) {
        String order=tbPdfType.getOrder();
        if(order==null){
            order="createTime";
        }
        if(tbPdfType.getModel()==null){
            tbPdfType.setModel(new TbPdfType());
        }
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Example<TbPdfType> example=Example.of(tbPdfType.getModel());
        return tbPdfTypeJpa.findAll(example,sort);
    }

    @Override
    public PageTable<TbPdfType> selectByPage(BaseTable<TbPdfType> tbPdfType) {
        Sort sort;
        if(tbPdfType.getOrder()==null){
            tbPdfType.setOrder("createTime");
        }
        if(tbPdfType.getAscDesc()==null||!tbPdfType.getAscDesc().equals("1")){
            sort = new Sort(Sort.Direction.DESC,tbPdfType.getOrder());
        }else {
            sort = new Sort(Sort.Direction.ASC,tbPdfType.getOrder());
        }
        if(tbPdfType.getPageNum()==null||tbPdfType.getPageSize()==null){
            tbPdfType.setPageNum(1);
            tbPdfType.setPageSize(8);
        }
        PageRequest pageRequest= PageRequest.of(tbPdfType.getPageNum()-1,tbPdfType.getPageSize(), sort);
        TbPdfTypeSpec tbPdfTypeSpec=new TbPdfTypeSpec(tbPdfType);
        Page<TbPdfType> page=tbPdfTypeJpa.findAll(tbPdfTypeSpec,pageRequest);
        return TableUtil.copyTableList(page);
    }

    private class TbPdfTypeSpec implements Specification<TbPdfType> {

        private BaseTable<TbPdfType> baseTable;

        TbPdfTypeSpec( BaseTable<TbPdfType> baseTable){
            this.baseTable=baseTable;
        }

        @Override
        public Predicate toPredicate(Root<TbPdfType> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return TableUtil.toPredicate(baseTable,root,criteriaQuery,criteriaBuilder);
        }
    }
}
