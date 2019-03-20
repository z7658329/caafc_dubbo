package com.micro.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.micro.api.mysql.TbDepartmentService;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.api.mysql.model.TbDepartment;
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
public class TbDepartmentController {

    @Reference(check = false)
    private TbDepartmentService tbDepartmentService;


    @PostMapping("/department/insert")
    public TbDepartment insert(@RequestBody TbDepartment tbDepartment){
        return tbDepartmentService.insert(tbDepartment);
    }

    @PostMapping("/department/insert/batch")
    Integer insertBatch(@RequestBody List<TbDepartment> tbDepartments){
        return tbDepartmentService.insertBatch(tbDepartments);
    }

    @PutMapping("/department/update/id")
    TbDepartment updateById(@RequestBody TbDepartment tbDepartment){
        return tbDepartmentService.updateById(tbDepartment);
    }

    @PutMapping("/department/update/batch")
    Integer updateBatch(@RequestBody List<TbDepartment> tbDepartments){
        return tbDepartmentService.updateBatch(tbDepartments);
    }

    @DeleteMapping("/department/delete/{id}")
    Integer deleteById(@PathVariable String id){
        return tbDepartmentService.deleteById(id);
    }

    @DeleteMapping("/department/delete/batch")
    Integer deleteBatch(@RequestBody List<TbDepartment> tbDepartments){
        return tbDepartmentService.deleteBatch(tbDepartments);
    }

    @GetMapping("/department/select/{id}")
    public TbDepartment selectById(@PathVariable String id){
        return tbDepartmentService.selectById(id);
    }

    @PostMapping("/department/select/all/get")
    List<TbDepartment> selectAll(@RequestBody BaseTable<TbDepartment> tbDepartment){
        return tbDepartmentService.selectAll(tbDepartment);
    }

    @PostMapping("/department/select/page/get")
    PageTable<TbDepartment> selectByPage(@RequestBody BaseTable<TbDepartment> tbDepartment){
        return tbDepartmentService.selectByPage(tbDepartment);
    }
}
