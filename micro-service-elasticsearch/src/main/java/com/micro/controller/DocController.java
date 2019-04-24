package com.micro.controller;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.controller
 * Author:   hhc
 * Date:     2018/11/22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import com.micro.api.elasticsearch.DocService;
import com.micro.api.elasticsearch.model.Doc;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doc")
public class DocController {

    @Autowired
    private DocService docService;


    @PostMapping("/insert")
    public Doc save(@RequestBody Doc doc){
        return docService.insert(doc);
    }

    @DeleteMapping("/delete/{id}")
    public int delete(@PathVariable String id){
        return docService.deleteById(id);
    }

    @PutMapping("/update/id")
    public Doc update(@RequestBody Doc doc){
        return docService.updateById(doc);
    }

    @GetMapping("/select/{id}")
    public Doc getOne(@PathVariable String id){
        Doc doc = docService.selectById(id);
        return doc;
    }

    @PostMapping("/select/page/get")
    public PageTable<Doc> selectByString(@RequestBody BaseTable<String> baseTable){
        return docService.selectByString(baseTable);
    }
}
