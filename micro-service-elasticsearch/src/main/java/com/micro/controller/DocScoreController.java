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
import com.micro.api.elasticsearch.DocService;
import com.micro.api.elasticsearch.SearchService;
import com.micro.api.elasticsearch.model.Doc;
import com.micro.api.elasticsearch.model.DocScore;
import com.micro.api.elasticsearch.model.Institution;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class DocScoreController {

    @Autowired
    private  SearchService searchService;

    @PostMapping("/selectDocByPage/get")
    PageTable<DocScore> selectDocByPage(@RequestBody BaseTable<Doc> doc){
        return searchService.selectDocByPage(doc);
    }

    @PostMapping("/selectInstitutionByPage/get")
    PageTable<Institution> selectInstitutionByPage(@RequestBody BaseTable<Institution> institution){
        return searchService.selectInstitutionByPage(institution);
    }


}
