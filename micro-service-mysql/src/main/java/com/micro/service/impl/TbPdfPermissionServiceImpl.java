package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.mysql.TbPdfPermissionService;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbPdfPermission;
import com.micro.dao.TbPdfPermissionJpa;
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
public class TbPdfPermissionServiceImpl implements TbPdfPermissionService {

    @Autowired
    private TbPdfPermissionJpa tbPdfPermissionJpa;


    @Override
    public TbPdfPermission insert(TbPdfPermission tbPdfPermission) {
        if(tbPdfPermission.getId()==null){
            tbPdfPermission.setId(TableUtil.uuid());
        }
        if(tbPdfPermission.getCreateTime()==null){
            tbPdfPermission.setCreateTime(new Date());
        }
        return tbPdfPermissionJpa.saveAndFlush(tbPdfPermission);
    }

    @Override
    public Integer insertBatch(List<TbPdfPermission> tbPdfPermissions) {
        for(TbPdfPermission tbPdfPermission:tbPdfPermissions){
            insert(tbPdfPermission);
        }
        return tbPdfPermissions.size();
    }

    @Override
    public TbPdfPermission updateById(TbPdfPermission tbPdfPermission) {
        if(tbPdfPermission.getUpdateTime()==null){
            tbPdfPermission.setUpdateTime(new Date());
        }
        TbPdfPermission source =tbPdfPermissionJpa.getOne(tbPdfPermission.getId());
        BeanUtils.copyProperties(source,tbPdfPermission,TableUtil.getNoNullProperties(tbPdfPermission));
        tbPdfPermissionJpa.saveAndFlush(tbPdfPermission);
        return tbPdfPermission;
    }

    @Override
    public Integer updateBatch(List<TbPdfPermission> tbPdfPermissions) {
        for(TbPdfPermission tbPdfPermission:tbPdfPermissions){
            updateById(tbPdfPermission);
        }
        return tbPdfPermissions.size();
    }

    @Override
    public Integer deleteById(String id) {
        TbPdfPermission tbPdfPermission = selectById(id);
        tbPdfPermission.setIsDelete(1);
        tbPdfPermission.setUpdateTime(new Date());
        tbPdfPermissionJpa.saveAndFlush(tbPdfPermission);
        return 1;
    }

    @Override
    public Integer deleteBatch(List<TbPdfPermission> tbPdfPermissions) {
        tbPdfPermissionJpa.deleteAll(tbPdfPermissions);
        return tbPdfPermissions.size();
    }

    @Override
    public TbPdfPermission selectById(String id) {
        return tbPdfPermissionJpa.getOne(id);
    }

    @Override
    public List<TbPdfPermission> selectAll(BaseTable<TbPdfPermission> tbPdfPermission) {
        String order=tbPdfPermission.getOrder();
        if(order==null){
            order="createTime";
        }
        if(tbPdfPermission.getModel()==null){
            tbPdfPermission.setModel(new TbPdfPermission());
        }
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Example<TbPdfPermission> example=Example.of(tbPdfPermission.getModel());
        return tbPdfPermissionJpa.findAll(example,sort);
    }

    @Override
    public PageTable<TbPdfPermission> selectByPage(BaseTable<TbPdfPermission> tbPdfPermission) {
        Sort sort;
        if(tbPdfPermission.getOrder()==null){
            tbPdfPermission.setOrder("createTime");
        }
        if(tbPdfPermission.getAscDesc()==null||!tbPdfPermission.getAscDesc().equals("1")){
            sort = new Sort(Sort.Direction.DESC,tbPdfPermission.getOrder());
        }else {
            sort = new Sort(Sort.Direction.ASC,tbPdfPermission.getOrder());
        }
        if(tbPdfPermission.getPageNum()==null||tbPdfPermission.getPageSize()==null){
            tbPdfPermission.setPageNum(1);
            tbPdfPermission.setPageSize(8);
        }
        PageRequest pageRequest= PageRequest.of(tbPdfPermission.getPageNum()-1,tbPdfPermission.getPageSize(), sort);
        TbPdfPermissionSpec tbPdfPermissionSpec=new TbPdfPermissionSpec(tbPdfPermission);
        Page<TbPdfPermission> page=tbPdfPermissionJpa.findAll(tbPdfPermissionSpec,pageRequest);
        return TableUtil.copyTableList(page);
    }

    private class TbPdfPermissionSpec implements Specification<TbPdfPermission> {

        private BaseTable<TbPdfPermission> baseTable;

        TbPdfPermissionSpec( BaseTable<TbPdfPermission> baseTable){
            this.baseTable=baseTable;
        }

        @Override
        public Predicate toPredicate(Root<TbPdfPermission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return TableUtil.toPredicate(baseTable,root,criteriaQuery,criteriaBuilder);
        }
    }
}
