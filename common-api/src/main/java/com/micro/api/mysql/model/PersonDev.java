package com.micro.api.mysql.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.mysql.model
 * Author:   hhc
 * Date:     2019/3/18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
public class PersonDev implements Serializable {

    private TbPerson tbPerson;

    private List<TbPerson> tbPersons;

    private TbDepartment tbDepartment;

    private List<TbDepartment> tbDepartments;
}
