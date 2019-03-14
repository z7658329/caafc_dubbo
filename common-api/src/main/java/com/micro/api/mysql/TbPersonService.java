package com.micro.api.mysql;

import com.micro.api.mysql.model.BaseTable;
import com.micro.api.mysql.model.PageTable;
import com.micro.api.mysql.model.TbPerson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.mysql
 * Author:   hhc
 * Date:     2019/3/13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface TbPersonService {

    TbPerson insert(TbPerson tbPerson);

    Integer insertBatch(List<TbPerson> tbPersons);

    TbPerson updateById(TbPerson tbPerson);

    Integer updateBatch(List<TbPerson> tbPersons);

    Integer deleteById(String id);

    Integer deleteBatch(List<TbPerson> tbPersons);

    TbPerson selectById(String id);

    List<TbPerson> selectAll(BaseTable<TbPerson> tbPerson);

    PageTable<TbPerson> selectByPage(BaseTable<TbPerson> tbPerson);

}
