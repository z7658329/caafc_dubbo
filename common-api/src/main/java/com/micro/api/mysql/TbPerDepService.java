package com.micro.api.mysql;

import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.api.mysql.model.TbPerDep;

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
public interface TbPerDepService {

    TbPerDep insert(TbPerDep tbPerDep);

    Integer insertBatch(List<TbPerDep> tbPerDeps);

    TbPerDep updateById(TbPerDep tbPerDep);

    Integer updateBatch(List<TbPerDep> tbPerDeps);

    Integer deleteById(String id);

    Integer deleteBatch(List<TbPerDep> tbPerDeps);

    TbPerDep selectById(String id);

    List<TbPerDep> selectAll(BaseTable<TbPerDep> tbPerDep);

    PageTable<TbPerDep> selectByPage(BaseTable<TbPerDep> tbPerDep);
    
}
