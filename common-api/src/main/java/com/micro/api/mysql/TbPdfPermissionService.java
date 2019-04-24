package com.micro.api.mysql;

import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbPdfPermission;

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
public interface TbPdfPermissionService {
    
    TbPdfPermission insert(TbPdfPermission tbPdfPermission);

    Integer insertBatch(List<TbPdfPermission> tbPdfPermissions);

    TbPdfPermission updateById(TbPdfPermission tbPdfPermission);

    Integer updateBatch(List<TbPdfPermission> tbPdfPermissions);

    Integer deleteById(String id);

    Integer deleteBatch(List<TbPdfPermission> tbPdfPermissions);

    TbPdfPermission selectById(String id);

    List<TbPdfPermission> selectAll(BaseTable<TbPdfPermission> tbPdfPermission);

    PageTable<TbPdfPermission> selectByPage(BaseTable<TbPdfPermission> tbPdfPermission);
}
