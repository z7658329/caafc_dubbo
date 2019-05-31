package com.micro.test;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.test
 * Author:   hhc
 * Date:     2019/5/16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@FeignClient(url = "${app.url}",name="engine")
public interface Test {

    @RequestMapping(value="/mysql/department/select/page/get",method= RequestMethod.POST)
    JSONObject getEngineMesasge(@RequestParam("uid") String uid, @RequestBody JSONObject j, @RequestHeader("userId")String userId);

}
