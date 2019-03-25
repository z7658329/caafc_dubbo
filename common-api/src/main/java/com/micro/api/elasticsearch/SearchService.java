package com.micro.api.elasticsearch;

import com.micro.api.elasticsearch.model.Doc;
import com.micro.api.elasticsearch.model.DocScore;
import com.micro.api.elasticsearch.model.Institution;
import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.api.mysql.model.TbDepartment;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.elasticsearch.model
 * Author:   hhc
 * Date:     2019/3/20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface SearchService {

    PageTable<DocScore> selectDocByPage(BaseTable<Doc> doc);

    PageTable<Institution> selectInstitutionByPage(BaseTable<Institution> institution);
}


