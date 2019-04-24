package com.micro.util;


import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import org.springframework.data.domain.Page;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.utils
 * Author:   hhc
 * Date:     2018/9/12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class TableUtil extends BaseTableUtil {

    public static <T> Predicate toPredicate(BaseTable<T> baseTable, Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
        Map<String,Object> model=getNoNullMap(baseTable.getModel());
        if(model!=null){
            for(String prop:model.keySet()){
                predicates.add(criteriaBuilder.equal(root.get(prop),model.get(prop)));
            }
        }
        //起止时间
        if(baseTable.getStart()!=null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime"), new Date(baseTable.getStart())));
        }

        if(baseTable.getEnd()!=null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime"), new Date(baseTable.getEnd())));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    public static <T> PageTable copyTableList(Page<T> page){
        PageTable<T>pageTable=new PageTable<T>();
        pageTable.setTotalElements(page.getTotalElements());
        pageTable.setTotalPages(page.getTotalPages());
        pageTable.setModels(page.getContent());
        return pageTable ;
    }



}
