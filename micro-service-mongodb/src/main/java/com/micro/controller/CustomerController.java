package com.micro.controller;

import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.Customer;
import com.micro.api.mongodb.model.TbDepartment;
import com.micro.dao.CustomerRepository;
import com.micro.util.TableUtil;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.micro.util.TableUtil.getUpdateByObject;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.service.impl
 * Author:   hhc
 * Date:     2019/4/12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/customer/insert")
    public Customer insert(@RequestBody Customer customer){
        if(customer.getCreateTime()==null){
            customer.setCreateTime(new Date());
        }
        return customerRepository.insert(customer);
    }

    @PostMapping("/customer/insert/batch")
    Integer insertBatch(@RequestBody List<Customer> customers){
        return customerRepository.insert(customers).size();
    }

    @PutMapping("/customer/update/id")
    Customer updateById(@RequestBody Customer customer){
        Query query=new Query(Criteria.where("id").is(customer));
        Update update = getUpdateByObject(customer);
        UpdateResult updateResult=mongoTemplate.updateFirst(query,update,Customer.class);
        System.out.println(updateResult);
        return customer;
    }

    @PutMapping("/customer/update/batch")
    Integer updateBatch(@RequestBody List<Customer> customers){


        return null;
    }

    @DeleteMapping("/customer/delete/{id}")
    Integer deleteById(@PathVariable String id){
        customerRepository.deleteById(id);
        return 1;
    }

    @DeleteMapping("/customer/delete/batch")
    Integer deleteBatch(@RequestBody List<Customer> customers){
        customerRepository.deleteAll(customers);
        return customers.size();
    }

    @GetMapping("/customer/select/{id}")
    public Customer selectById(@PathVariable String id){
        return customerRepository.findById(id).get();
    }

    @PostMapping("/customer/select/all/get")
    List<Customer> selectAll(@RequestBody BaseTable<Customer> customer){
        String order=customer.getOrder();
        if(order==null){
            order="createTime";
        }
        if(customer.getModel()==null){
            customer.setModel(new Customer());
        }
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Example<Customer> example=Example.of(customer.getModel());
        return customerRepository.findAll(example,sort);
    }

    @PostMapping("/customer/select/page/get")
    PageTable<Customer> selectByPage(@RequestBody BaseTable<Customer> customer){
        String order=customer.getOrder();
        if(order==null){
            order="createTime";
        }
        if(customer.getModel()==null){
            customer.setModel(new Customer());
        }
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Example<Customer> example=Example.of(customer.getModel());
        PageRequest pageRequest= PageRequest.of(customer.getPageNum()-1,customer.getPageSize(),sort);
        Page<Customer> page=customerRepository.findAll(example,pageRequest);
        return TableUtil.copyTableList(page);
    }

    @PostMapping("/customer/select/page/time/get")
    PageTable<Customer> selectByTimePage(@RequestBody BaseTable<Customer> customer){
        String order=customer.getOrder();
        if(order==null){
            order="createTime";
        }
        if(customer.getModel()==null){
            customer.setModel(new Customer());
        }
        Sort sort = new Sort(Sort.Direction.DESC,order);
        Query query=TableUtil.getQueryByObject(customer.getModel());
        if(customer.getStart()!=null){
            query.addCriteria(Criteria.where("createTime").gte(new Date(customer.getStart())));
        }
        if(customer.getEnd()!=null){
            query.addCriteria(Criteria.where("createTime").lte(new Date(customer.getEnd())));
        }
        PageTable<Customer>pageTable=new PageTable<>();
        pageTable.setTotalElements(mongoTemplate.count(query,Customer.class));
        PageRequest pageRequest= PageRequest.of(customer.getPageNum()-1,customer.getPageSize(),sort);
        query.with(sort).with(pageRequest);
        List<Customer> customers=mongoTemplate.find(query,Customer.class);
        pageTable.setModels(customers);
        return pageTable;
    }

    @PostMapping("/customer/Aggregation/get")
    void aggregation(String s){
        Criteria criteria=Criteria.where("lastName").is(s);
        Sort sort = new Sort(Sort.Direction.DESC,"cou");
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("firstName").count().as("cou"),
                Aggregation.sort(sort)
        );
        AggregationResults aggregationResults=mongoTemplate.aggregate(aggregation,"customer",Customer.class);
        System.out.println(aggregationResults);
    }
}
