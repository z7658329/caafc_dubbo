package com.micro.api.mysql.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
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
@Table(indexes ={@Index(columnList ="userId")})
@Data
public class TbPerson implements Serializable {

    @Id
    private String id;

    @ApiModelProperty(value = "用户名", example = "abcd12345")
    private String name;

    @Column(length=100,nullable = false,unique = true)
    private String userId;

    private String password;


    private String orgAccountId;

    private String orgLevelId;

    private String orgPostId;

    private String orgDepartmentId;

    private String enable;

    private Date createTime;

    private Date updateTime;

}

