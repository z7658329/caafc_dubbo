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

import com.alibaba.dubbo.config.annotation.Reference;
import com.micro.api.elasticsearch.InstitutionService;
import com.micro.api.elasticsearch.model.Institution;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Reference(check = false)
    private InstitutionService institutionService;


    @PostMapping("/insert")
    public Institution save(@RequestBody Institution institution){
        return institutionService.insert(institution);
    }

    @DeleteMapping("/delete/id")
    public int delete(@RequestParam String id){
        return institutionService.deleteById(id);
    }

    @PutMapping("/update/id")
    public Institution update(@RequestBody Institution institution){
        return institutionService.updateById(institution);
    }

    @GetMapping("/select/{id}")
    public Institution getOne(@PathVariable String id){
        Institution institution = institutionService.selectById(id);
        return institution;
    }

    @PostMapping("/select/page/get")
    public PageTable<Institution> searchString(@RequestBody BaseTable<String> baseTable){
        return institutionService.selectByString(baseTable);
    }


}
