package com.micro.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.micro.api.mysql.TbPdfPermissionService;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbPdfPermission;
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
public class TbPdfPermissionController {

    @Reference(check = false)
    private TbPdfPermissionService tbPdfPermissionService;


    @PostMapping("/pdfPermission/insert")
    public TbPdfPermission insert(@RequestBody TbPdfPermission tbPdfPermission){
        return tbPdfPermissionService.insert(tbPdfPermission);
    }

    @PostMapping("/pdfPermission/insert/batch")
    Integer insertBatch(@RequestBody List<TbPdfPermission> tbPdfPermissions){
        return tbPdfPermissionService.insertBatch(tbPdfPermissions);
    }

    @PutMapping("/pdfPermission/update/id")
    TbPdfPermission updateById(@RequestBody TbPdfPermission tbPdfPermission){
        return tbPdfPermissionService.updateById(tbPdfPermission);
    }

    @PutMapping("/pdfPermission/update/batch")
    Integer updateBatch(@RequestBody List<TbPdfPermission> tbPdfPermissions){
        return tbPdfPermissionService.updateBatch(tbPdfPermissions);
    }

    @DeleteMapping("/pdfPermission/delete/{id}")
    Integer deleteById(@PathVariable String id){
        return tbPdfPermissionService.deleteById(id);
    }

    @DeleteMapping("/pdfPermission/delete/batch")
    Integer deleteBatch(@RequestBody List<TbPdfPermission> tbPdfPermissions){
        return tbPdfPermissionService.deleteBatch(tbPdfPermissions);
    }

    @GetMapping("/pdfPermission/select/{id}")
    public TbPdfPermission selectById(@PathVariable String id){
        return tbPdfPermissionService.selectById(id);
    }

    @PostMapping("/pdfPermission/select/all/get")
    List<TbPdfPermission> selectAll(@RequestBody BaseTable<TbPdfPermission> tbPdfPermission){
        return tbPdfPermissionService.selectAll(tbPdfPermission);
    }

    @PostMapping("/pdfPermission/select/page/get")
    PageTable<TbPdfPermission> selectByPage(@RequestBody BaseTable<TbPdfPermission> tbPdfPermission){
        return tbPdfPermissionService.selectByPage(tbPdfPermission);
    }
}
