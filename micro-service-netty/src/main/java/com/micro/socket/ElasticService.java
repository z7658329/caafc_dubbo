package com.micro.socket;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.service
 * Author:   utils
 * Date:     2018/6/29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface ElasticService {
    String get(String index, String type, String id);
    String insert(String index, String type, String id, String jsonDocument);
    String insertBulk(String index, String type, String idName, String documents);
    String update(String index, String type, String id, String jsonIndexRequest);
    String delete(String index, String type, String id);


}
