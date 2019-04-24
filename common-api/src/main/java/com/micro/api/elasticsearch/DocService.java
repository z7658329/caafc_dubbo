package com.micro.api.elasticsearch;

import com.micro.api.elasticsearch.model.Doc;
import com.micro.api.mongodb.model.BaseTable;
import com.micro.api.mongodb.model.PageTable;

import java.util.List;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.elasticsearch
 * Author:   hhc
 * Date:     2019/3/6
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface DocService {

    Doc insert(Doc doc);

    Integer insertBatch(List<Doc> docs);

    Integer deleteById(String id);

    Integer deleteBatch(Doc doc);

    Doc updateById(Doc doc);

    Doc selectById(String id);

    PageTable<Doc> selectByString(BaseTable<String> baseTable);
}
