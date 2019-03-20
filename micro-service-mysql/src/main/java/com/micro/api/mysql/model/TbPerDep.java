package com.micro.api.mysql.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.mysql.model
 * Author:   hhc
 * Date:     2019/3/15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@ApiModel(value = "人员部门关系表")
public class TbPerDep implements Serializable {

    @Id
    private String id;

    @ApiModelProperty(value = "人员id", example = "1")
    private String perId;

    @ApiModelProperty(value = "部门id", example = "1")
    private String devId;

}
