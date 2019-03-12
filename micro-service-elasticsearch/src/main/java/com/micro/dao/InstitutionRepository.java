package com.micro.dao;

import com.micro.api.elasticsearch.model.Institution;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.dao
 * Author:   hhc
 * Date:     2018/11/22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Component
public interface InstitutionRepository extends ElasticsearchRepository<Institution,String> {
}
