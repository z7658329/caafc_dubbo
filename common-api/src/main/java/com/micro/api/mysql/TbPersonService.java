package com.micro.api.mysql;

import com.micro.api.mysql.model.TbPerson;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    TbPerson selTbPersonId(String id);

    TbPerson addTbPersonId(TbPerson tbPerson);
}
