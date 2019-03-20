package com.micro.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.micro.api.mysql.TbPerDepService;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.api.mysql.model.TbPerDep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.controller
 * Author:   hhc
 * Date:     2019/3/13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@RestController
@RequestMapping("/mysql")
public class TbeController {

    @Reference(check = false)
    private TbPerDepService tbPerDepService;


    @PostMapping("/perDep/insert")
    public TbPerDep insert(@RequestBody TbPerDep tbPerDep){
        return tbPerDepService.insert(tbPerDep);
    }

    @PostMapping("/perDep/insert/batch")
    Integer insertBatch(@RequestBody List<TbPerDep> tbPerDeps){
        return tbPerDepService.insertBatch(tbPerDeps);
    }

    @PutMapping("/perDep/update/id")
    TbPerDep updateById(@RequestBody TbPerDep tbPerDep){
        return tbPerDepService.updateById(tbPerDep);
    }

    @PutMapping("/perDep/update/batch")
    Integer updateBatch(@RequestBody List<TbPerDep> tbPerDeps){
        return tbPerDepService.updateBatch(tbPerDeps);
    }

    @DeleteMapping("/perDep/delete/{id}")
    Integer deleteById(@PathVariable String id){
        return tbPerDepService.deleteById(id);
    }

    @DeleteMapping("/perDep/delete/batch")
    Integer deleteBatch(@RequestBody List<TbPerDep> tbPerDeps){
        return tbPerDepService.deleteBatch(tbPerDeps);
    }

    @GetMapping("/perDep/select/{id}")
    public TbPerDep selectById(@PathVariable String id){
        return tbPerDepService.selectById(id);
    }

    @PostMapping("/perDep/select/all/get")
    List<TbPerDep> selectAll(@RequestBody BaseTable<TbPerDep> tbPerDep){
        return tbPerDepService.selectAll(tbPerDep);
    }

    @PostMapping("/perDep/select/page/get")
    PageTable<TbPerDep> selectByPage(@RequestBody BaseTable<TbPerDep> tbPerDep){
        return tbPerDepService.selectByPage(tbPerDep);
    }
}
