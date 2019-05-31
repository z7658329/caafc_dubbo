package com.micro.netty.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.mysql.TbPdfModelService;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbPdfModel;
import com.micro.dao.TbPdfModelJpa;
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
 * FileName:  com.micro.netty.impl
 * Author:   hhc
 * Date:     2019/3/15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
@Slf4j
public class TbPdfModelServiceImpl implements TbPdfModelService {
    
    @Autowired
    private TbPdfModelJpa tbPdfModelJpa;


    @Override
    public TbPdfModel insert(TbPdfModel tbPdfModel) {
        if(tbPdfModel.getId()==null){
            tbPdfModel.setId(TableUtil.uuid());
        }
        if(tbPdfModel.getCreateTime()==null){
            tbPdfModel.setCreateTime(new Date());
        }
        return tbPdfModelJpa.saveAndFlush(tbPdfModel);
    }

    @Override
    public Integer insertBatch(List<TbPdfModel> tbPdfModels) {
        for(TbPdfModel tbPdfModel:tbPdfModels){
            insert(tbPdfModel);
        }
        return tbPdfModels.size();
    }

    @Override
    public TbPdfModel updateById(TbPdfModel tbPdfModel) {
        if(tbPdfModel.getUpdateTime()==null){
            tbPdfModel.setUpdateTime(new Date());
        }
        TbPdfModel source =tbPdfModelJpa.getOne(tbPdfModel.getId());
        BeanUtils.copyProperties(source,tbPdfModel,TableUtil.getNoNullProperties(tbPdfModel));
        tbPdfModelJpa.saveAndFlush(tbPdfModel);
        return tbPdfModel;
    }

    @Override
    public Integer updateBatch(List<TbPdfModel> tbPdfModels) {
        for(TbPdfModel tbPdfModel:tbPdfModels){
            updateById(tbPdfModel);
        }
        return tbPdfModels.size();
    }

    @Override
    public Integer deleteById(String id) {
        TbPdfModel tbPdfModel = selectById(id);
        tbPdfModel.setIsDelete(1);
        tbPdfModel.setUpdateTime(new Date());
        tbPdfModelJpa.saveAndFlush(tbPdfModel);
        return 1;
    }

    @Override
    public Integer deleteBatch(List<TbPdfModel> tbPdfModels) {
        tbPdfModelJpa.deleteAll(tbPdfModels);
        return tbPdfModels.size();
    }

    @Override
    public TbPdfModel selectById(String id) {
        return tbPdfModelJpa.getOne(id);
    }

    @Override
    public List<TbPdfModel> selectAll(BaseTable<TbPdfModel> tbPdfModel) {
        String order=tbPdfModel.getOrder();
        if(order==null){
            order="createTime";
        }
        if(tbPdfModel.getModel()==null){
            tbPdfModel.setModel(new TbPdfModel());
        }
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Example<TbPdfModel> example=Example.of(tbPdfModel.getModel());
        return tbPdfModelJpa.findAll(example,sort);
    }

    @Override
    public PageTable<TbPdfModel> selectByPage(BaseTable<TbPdfModel> tbPdfModel) {
        Sort sort;
        if(tbPdfModel.getOrder()==null){
            tbPdfModel.setOrder("createTime");
        }
        if(tbPdfModel.getAscDesc()==null||!tbPdfModel.getAscDesc().equals("1")){
            sort = new Sort(Sort.Direction.DESC,tbPdfModel.getOrder());
        }else {
            sort = new Sort(Sort.Direction.ASC,tbPdfModel.getOrder());
        }
        if(tbPdfModel.getPageNum()==null||tbPdfModel.getPageSize()==null){
            tbPdfModel.setPageNum(1);
            tbPdfModel.setPageSize(8);
        }
        PageRequest pageRequest= PageRequest.of(tbPdfModel.getPageNum()-1,tbPdfModel.getPageSize(), sort);
        TbPdfModelServiceImpl.TbPdfModelSpec tbPdfModelSpec=new TbPdfModelServiceImpl.TbPdfModelSpec(tbPdfModel);
        Page<TbPdfModel> page=tbPdfModelJpa.findAll(tbPdfModelSpec,pageRequest);
        return TableUtil.copyTableList(page);
    }

    private class TbPdfModelSpec implements Specification<TbPdfModel> {

        private BaseTable<TbPdfModel> baseTable;

        TbPdfModelSpec( BaseTable<TbPdfModel> baseTable){
            this.baseTable=baseTable;
        }

        @Override
        public Predicate toPredicate(Root<TbPdfModel> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            return TableUtil.toPredicate(baseTable,root,criteriaQuery,criteriaBuilder);
        }
    }
}
