package com.micro.api.mysql;

import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.api.mysql.model.TbDepartment;

import java.util.List;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.mysql
 * Author:   hhc
 * Date:     2019/3/15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface TbDepartmentService  {
    
    TbDepartment insert(TbDepartment tbDepartment);

    Integer insertBatch(List<TbDepartment> tbDepartments);

    TbDepartment updateById(TbDepartment tbDepartment);

    Integer updateBatch(List<TbDepartment> tbDepartments);

    Integer deleteById(String id);

    Integer deleteBatch(List<TbDepartment> tbDepartments);

    TbDepartment selectById(String id);

    List<TbDepartment> selectAll(BaseTable<TbDepartment> tbDepartment);

    PageTable<TbDepartment> selectByPage(BaseTable<TbDepartment> tbDepartment);
}
