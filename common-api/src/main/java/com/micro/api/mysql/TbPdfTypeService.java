package com.micro.api.mysql;

import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.api.mysql.model.TbPdfType;

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
public interface TbPdfTypeService {
    
    TbPdfType insert(TbPdfType tbPdfType);

    Integer insertBatch(List<TbPdfType> tbPdfTypes);

    TbPdfType updateById(TbPdfType tbPdfType);

    Integer updateBatch(List<TbPdfType> tbPdfTypes);

    Integer deleteById(String id);

    Integer deleteBatch(List<TbPdfType> tbPdfTypes);

    TbPdfType selectById(String id);

    List<TbPdfType> selectAll(BaseTable<TbPdfType> tbPdfType);

    PageTable<TbPdfType> selectByPage(BaseTable<TbPdfType> tbPdfType);
}
