package com.micro.api.mysql.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.model.database
 * Author:   hhc
 * Date:     2018/8/16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class TbPerson implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @ApiModelProperty(value = "用户id", example = "abcd12345")
    private String id;

    private String name;

    private String userId;

    private String password;

    private String orgAccountId;

    private String orgLevelId;

    private String orgPostId;

    private String orgDepartmentId;

    private String enable;

    private Date create_time;

    private Date update_time;


}

