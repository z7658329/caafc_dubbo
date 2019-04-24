package com.micro.api.mysql;

import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;
import com.micro.api.mongodb.model.TbPdfModel;

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
public interface TbPdfModelService {
    
    TbPdfModel insert(TbPdfModel tbPdfModel);

    Integer insertBatch(List<TbPdfModel> tbPdfModels);

    TbPdfModel updateById(TbPdfModel tbPdfModel);

    Integer updateBatch(List<TbPdfModel> tbPdfModels);

    Integer deleteById(String id);

    Integer deleteBatch(List<TbPdfModel> tbPdfModels);

    TbPdfModel selectById(String id);

    List<TbPdfModel> selectAll(BaseTable<TbPdfModel> tbPdfModel);

    PageTable<TbPdfModel> selectByPage(BaseTable<TbPdfModel> tbPdfModel);
}
