package com.micro.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.micro.api.mysql.TbPdfTypeService;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbPdfType;
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
public class TbPdfTypeController {

    @Reference(check = false)
    private TbPdfTypeService tbPdfTypeService;


    @PostMapping("/pdfType/insert")
    public TbPdfType insert(@RequestBody TbPdfType tbPdfType){
        return tbPdfTypeService.insert(tbPdfType);
    }

    @PostMapping("/pdfType/insert/batch")
    Integer insertBatch(@RequestBody List<TbPdfType> tbPdfTypes){
        return tbPdfTypeService.insertBatch(tbPdfTypes);
    }

    @PutMapping("/pdfType/update/id")
    TbPdfType updateById(@RequestBody TbPdfType tbPdfType){
        return tbPdfTypeService.updateById(tbPdfType);
    }

    @PutMapping("/pdfType/update/batch")
    Integer updateBatch(@RequestBody List<TbPdfType> tbPdfTypes){
        return tbPdfTypeService.updateBatch(tbPdfTypes);
    }

    @DeleteMapping("/pdfType/delete/{id}")
    Integer deleteById(@PathVariable String id){
        return tbPdfTypeService.deleteById(id);
    }

    @DeleteMapping("/pdfType/delete/batch")
    Integer deleteBatch(@RequestBody List<TbPdfType> tbPdfTypes){
        return tbPdfTypeService.deleteBatch(tbPdfTypes);
    }

    @GetMapping("/pdfType/select/{id}")
    public TbPdfType selectById(@PathVariable String id){
        return tbPdfTypeService.selectById(id);
    }

    @PostMapping("/pdfType/select/all/get")
    List<TbPdfType> selectAll(@RequestBody BaseTable<TbPdfType> tbPdfType){
        return tbPdfTypeService.selectAll(tbPdfType);
    }

    @PostMapping("/pdfType/select/page/get")
    PageTable<TbPdfType> selectByPage(@RequestBody BaseTable<TbPdfType> tbPdfType){
        return tbPdfTypeService.selectByPage(tbPdfType);
    }
}
