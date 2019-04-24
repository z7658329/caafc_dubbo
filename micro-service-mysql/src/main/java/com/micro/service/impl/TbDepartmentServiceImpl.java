package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.mysql.TbDepartmentService;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbDepartment;
import com.micro.dao.TbDepartmentJpa;
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
public class TbDepartmentServiceImpl implements TbDepartmentService {

    @Autowired
    private TbDepartmentJpa tbDepartmentJpa;


    @Override
    public TbDepartment insert(TbDepartment tbDepartment) {
        if(tbDepartment.getId()==null){
            tbDepartment.setId(TableUtil.uuid());
        }
        if(tbDepartment.getCreateTime()==null){
            tbDepartment.setCreateTime(new Date());
        }
        return tbDepartmentJpa.saveAndFlush(tbDepartment);
    }

    @Override
    public Integer insertBatch(List<TbDepartment> tbDepartments) {
        for(TbDepartment tbDepartment:tbDepartments){
            insert(tbDepartment);
        }
        return tbDepartments.size();
    }

    @Override
    public TbDepartment updateById(TbDepartment tbDepartment) {
        if(tbDepartment.getUpdateTime()==null){
            tbDepartment.setUpdateTime(new Date());
        }
        TbDepartment source =tbDepartmentJpa.getOne(tbDepartment.getId());
        BeanUtils.copyProperties(source,tbDepartment,TableUtil.getNoNullProperties(tbDepartment));
        tbDepartmentJpa.saveAndFlush(tbDepartment);
        return tbDepartment;
    }

    @Override
    public Integer updateBatch(List<TbDepartment> tbDepartments) {
        for(TbDepartment tbDepartment:tbDepartments){
            updateById(tbDepartment);
        }
        return tbDepartments.size();
    }

    @Override
    public Integer deleteById(String id) {
        TbDepartment tbDepartment = selectById(id);
        tbDepartment.setIsDelete(1);
        tbDepartment.setUpdateTime(new Date());
        tbDepartmentJpa.saveAndFlush(tbDepartment);
        return 1;
    }

    @Override
    public Integer deleteBatch(List<TbDepartment> tbDepartments) {
        tbDepartmentJpa.deleteAll(tbDepartments);
        return tbDepartments.size();
    }

    @Override
    public TbDepartment selectById(String id) {
        return tbDepartmentJpa.getOne(id);
    }

    @Override
    public List<TbDepartment> selectAll(BaseTable<TbDepartment> tbDepartment) {
        String order=tbDepartment.getOrder();
        if(order==null){
            order="createTime";
        }
        if(tbDepartment.getModel()==null){
            tbDepartment.setModel(new TbDepartment());
        }
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Example<TbDepartment> example=Example.of(tbDepartment.getModel());
        return tbDepartmentJpa.findAll(example,sort);
    }

    @Override
    public PageTable<TbDepartment> selectByPage(BaseTable<TbDepartment> tbDepartment) {
        Sort sort;
        if(tbDepartment.getOrder()==null){
            tbDepartment.setOrder("createTime");
        }
        if(tbDepartment.getAscDesc()==null||!tbDepartment.getAscDesc().equals("1")){
            sort = new Sort(Sort.Direction.DESC,tbDepartment.getOrder());
        }else {
            sort = new Sort(Sort.Direction.ASC,tbDepartment.getOrder());
        }
        if(tbDepartment.getPageNum()==null||tbDepartment.getPageSize()==null){
            tbDepartment.setPageNum(1);
            tbDepartment.setPageSize(8);
        }
        PageRequest pageRequest= PageRequest.of(tbDepartment.getPageNum()-1,tbDepartment.getPageSize(), sort);
        TbDepartmentSpec tbDepartmentSpec=new TbDepartmentSpec(tbDepartment);
        Page<TbDepartment> page=tbDepartmentJpa.findAll(tbDepartmentSpec,pageRequest);
        return TableUtil.copyTableList(page);
    }

    private class TbDepartmentSpec implements Specification<TbDepartment> {

        private BaseTable<TbDepartment> baseTable;

        TbDepartmentSpec( BaseTable<TbDepartment> baseTable){
            this.baseTable=baseTable;
        }

        @Override
        public Predicate toPredicate(Root<TbDepartment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return TableUtil.toPredicate(baseTable,root,criteriaQuery,criteriaBuilder);
        }
    }
}
