package com.micro.api.elasticsearch;

import com.micro.api.elasticsearch.model.Doc;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;

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

    Doc save(Doc doc);

    int delete(String id);

    Doc update(Doc doc);

    Doc getOne(String id);

    PageTable<Doc> searchString(BaseTable<String> baseTable);
}
