package com.micro.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.model
 * Author:   hhc
 * Date:     2019/6/3
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
@AllArgsConstructor
public class HttpRequest {

    private String path;
    private String method;
    private Map<String,String>parameters;
    private Map<String,String> headers;
    private String content;
}
