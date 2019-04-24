package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.mysql.TbPersonService;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbPerson;
import com.micro.dao.TbPersonJpa;
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
public class TbPersonServiceImpl implements TbPersonService {

    @Autowired
    private TbPersonJpa tbPersonJpa;


    @Override
    public TbPerson insert(TbPerson tbPerson) {
        if(tbPerson.getId()==null){
            tbPerson.setId(TableUtil.uuid());
        }
        if(tbPerson.getCreateTime()==null){
            tbPerson.setCreateTime(new Date());
        }
        return tbPersonJpa.saveAndFlush(tbPerson);
    }

    @Override
    public Integer insertBatch(List<TbPerson> tbPersons) {
        for(TbPerson tbPerson:tbPersons){
            insert(tbPerson);
        }
        return tbPersons.size();
    }

    @Override
    public TbPerson updateById(TbPerson tbPerson) {
        if(tbPerson.getUpdateTime()==null){
            tbPerson.setUpdateTime(new Date());
        }
        TbPerson source =tbPersonJpa.getOne(tbPerson.getId());
        BeanUtils.copyProperties(source,tbPerson,TableUtil.getNoNullProperties(tbPerson));
        tbPersonJpa.saveAndFlush(tbPerson);
        return tbPerson;
    }

    @Override
    public Integer updateBatch(List<TbPerson> tbPersons) {
        for(TbPerson tbPerson:tbPersons){
            updateById(tbPerson);
        }
        return tbPersons.size();
    }

    @Override
    public Integer deleteById(String id) {
        TbPerson tbPerson = selectById(id);
        tbPerson.setIsDelete(1);
        tbPerson.setUpdateTime(new Date());
        tbPersonJpa.saveAndFlush(tbPerson);
        return 1;
    }

    @Override
    public Integer deleteBatch(List<TbPerson> tbPersons) {
        tbPersonJpa.deleteAll(tbPersons);
        return tbPersons.size();
    }

    @Override
    public TbPerson selectById(String id) {
        return tbPersonJpa.getOne(id);
    }

    @Override
    public List<TbPerson> selectAll(BaseTable<TbPerson> tbPerson) {
        String order=tbPerson.getOrder();
        if(order==null){
            order="createTime";
        }
        if(tbPerson.getModel()==null){
            tbPerson.setModel(new TbPerson());
        }
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Example<TbPerson> example=Example.of(tbPerson.getModel());
        return tbPersonJpa.findAll(example,sort);
    }

    @Override
    public PageTable<TbPerson> selectByPage(BaseTable<TbPerson> tbPerson) {
        Sort sort;
        if(tbPerson.getOrder()==null){
            tbPerson.setOrder("createTime");
        }
        if(tbPerson.getAscDesc()==null||!tbPerson.getAscDesc().equals("1")){
            sort = new Sort(Sort.Direction.DESC,tbPerson.getOrder());
        }else {
            sort = new Sort(Sort.Direction.ASC,tbPerson.getOrder());
        }
        if(tbPerson.getPageNum()==null||tbPerson.getPageSize()==null){
            tbPerson.setPageNum(1);
            tbPerson.setPageSize(8);
        }
        PageRequest pageRequest= PageRequest.of(tbPerson.getPageNum()-1,tbPerson.getPageSize(), sort);
        TbPersonSpec tbPersonSpec=new TbPersonSpec(tbPerson);
        Page<TbPerson> page=tbPersonJpa.findAll(tbPersonSpec,pageRequest);
        return TableUtil.copyTableList(page);
    }

    private class TbPersonSpec implements Specification<TbPerson> {

        private BaseTable<TbPerson> baseTable;

        TbPersonSpec( BaseTable<TbPerson> baseTable){
            this.baseTable=baseTable;
        }

        @Override
        public Predicate toPredicate(Root<TbPerson> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return TableUtil.toPredicate(baseTable,root,criteriaQuery,criteriaBuilder);
        }
    }
}
