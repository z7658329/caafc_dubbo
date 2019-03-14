package com.micro.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.micro.api.mysql.TbPersonService;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.api.mysql.model.TbPerson;
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
public class TbPersonController {

    @Reference(check = false)
    private TbPersonService tbPersonService;


    @PostMapping("/person/insert")
    public TbPerson insert(@RequestBody TbPerson tbPerson){
        return tbPersonService.insert(tbPerson);
    }

    @PostMapping("/person/insert/batch")
    Integer insertBatch(@RequestBody List<TbPerson> tbPersons){
        return tbPersonService.insertBatch(tbPersons);
    }

    @PutMapping("/person/update/id")
    TbPerson updateById(@RequestBody TbPerson tbPerson){
        return tbPersonService.updateById(tbPerson);
    }

    @PutMapping("/person/update/batch")
    Integer updateBatch(@RequestBody List<TbPerson> tbPersons){
        return tbPersonService.updateBatch(tbPersons);
    }

    @DeleteMapping("/person/delete/{id}")
    Integer deleteById(@PathVariable String id){
        return tbPersonService.deleteById(id);
    }

    @DeleteMapping("/person/delete/batch")
    Integer deleteBatch(@RequestBody List<TbPerson> tbPersons){
        return tbPersonService.deleteBatch(tbPersons);
    }

    @GetMapping("/person/select/{id}")
    public TbPerson selectById(@PathVariable String id){
        return tbPersonService.selectById(id);
    }

    @PostMapping("/person/select/all/get")
    List<TbPerson> selectAll(@RequestBody BaseTable<TbPerson> tbPerson){
        return tbPersonService.selectAll(tbPerson);
    }

    @PostMapping("/person/select/page/get")
    PageTable<TbPerson> selectByPage(@RequestBody BaseTable<TbPerson> tbPerson){
        return tbPersonService.selectByPage(tbPerson);
    }
}
