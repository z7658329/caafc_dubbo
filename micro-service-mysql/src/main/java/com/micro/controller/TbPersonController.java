package com.micro.controller;

import com.micro.api.mysql.TbPersonService;
import com.micro.api.mysql.model.TbPerson;
import com.micro.dao.TbPersonJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private TbPersonService tbPersonService;

    @GetMapping("/person/{id}")
    public TbPerson selTbPersonId(@PathVariable String id){
        return tbPersonService.selTbPersonId(id);
    }

    @PostMapping("/person")
    public TbPerson addTbPersonId(@RequestBody TbPerson tbPerson){
        return tbPersonService.addTbPersonId(tbPerson);
    }

}
