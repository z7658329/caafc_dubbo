package com.micro.api.mysql.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

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
@ApiModel(value = "pdf原模型")
public class TbPdfModel implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @ApiModelProperty(value = "父id 追溯历史版本", example = "1")
    private String pid;

    @ApiModelProperty(value = "名字", example = "1")
    private String title;

    @ApiModelProperty(value = "描述", example = "1")
    private String description;

    @ApiModelProperty(value = "文号", example = "1")
    private String fileNo;

    @ApiModelProperty(value = "业务类型", example = "1")
    private String pdfTypeId;

    @ApiModelProperty(value = "拟文部门 id ", example = "1")
    private String depId;

    @ApiModelProperty(value = "文件路径 ", example = "1")
    private String filePath;

    @ApiModelProperty(value = "               ", example = "0",dataType = "int")
    private Integer level;


    @ApiModelProperty(value = "生效时间", example = "0",dataType = "int")
    private Date effectTime;

    @ApiModelProperty(value = "状态 0 未生效 1生效", example = "0",dataType = "int")
    private Integer status;

    @ApiModelProperty(value = "1 删除", example = "1",dataType = "int")
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;
}
