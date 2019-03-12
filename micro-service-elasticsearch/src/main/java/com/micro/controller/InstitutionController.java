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

import com.micro.api.elasticsearch.InstitutionService;
import com.micro.api.elasticsearch.model.Institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;


    @PostMapping("/save")
    public Institution save(@RequestBody Institution institution){
        return institutionService.save(institution);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String id){
        institutionService.delete(id);
        return "success";
    }

    @PutMapping("/update")
    public Institution update(@RequestBody Institution institution){
        return institutionService.save(institution);
    }

    @GetMapping("/get/{id}")
    public Institution getOne(@PathVariable String id){
        Institution institution = institutionService.getOne(id);
        return institution;
    }

}
