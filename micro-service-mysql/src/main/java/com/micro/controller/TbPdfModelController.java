package com.micro.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.micro.api.mysql.TbPdfModelService;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.api.mysql.model.TbPdfModel;
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
public class TbPdfModelController {

    @Reference(check = false)
    private TbPdfModelService tbPdfModelService;


    @PostMapping("/pdfModel/insert")
    public TbPdfModel insert(@RequestBody TbPdfModel tbPdfModel){
        return tbPdfModelService.insert(tbPdfModel);
    }

    @PostMapping("/pdfModel/insert/batch")
    Integer insertBatch(@RequestBody List<TbPdfModel> tbPdfModels){
        return tbPdfModelService.insertBatch(tbPdfModels);
    }

    @PutMapping("/pdfModel/update/id")
    TbPdfModel updateById(@RequestBody TbPdfModel tbPdfModel){
        return tbPdfModelService.updateById(tbPdfModel);
    }

    @PutMapping("/pdfModel/update/batch")
    Integer updateBatch(@RequestBody List<TbPdfModel> tbPdfModels){
        return tbPdfModelService.updateBatch(tbPdfModels);
    }

    @DeleteMapping("/pdfModel/delete/{id}")
    Integer deleteById(@PathVariable String id){
        return tbPdfModelService.deleteById(id);
    }

    @DeleteMapping("/pdfModel/delete/batch")
    Integer deleteBatch(@RequestBody List<TbPdfModel> tbPdfModels){
        return tbPdfModelService.deleteBatch(tbPdfModels);
    }

    @GetMapping("/pdfModel/select/{id}")
    public TbPdfModel selectById(@PathVariable String id){
        return tbPdfModelService.selectById(id);
    }

    @PostMapping("/pdfModel/select/all/get")
    List<TbPdfModel> selectAll(@RequestBody BaseTable<TbPdfModel> tbPdfModel){
        return tbPdfModelService.selectAll(tbPdfModel);
    }

    @PostMapping("/pdfModel/select/page/get")
    PageTable<TbPdfModel> selectByPage(@RequestBody BaseTable<TbPdfModel> tbPdfModel){
        return tbPdfModelService.selectByPage(tbPdfModel);
    }
}
