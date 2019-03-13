package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.mysql.TbPersonService;
import com.micro.api.mysql.model.TbPerson;
import com.micro.dao.TbPersonJpa;
import com.micro.util.LG;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.service.impl
 * Author:   hhc
 * Date:     2019/3/13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Service
@Slf4j
public class TbPersonServiceImpl implements TbPersonService {

    @Autowired
    private TbPersonJpa tbPersonJpa;

    @Override
    public TbPerson selTbPersonId(String id) {
        log.info(LG.N(),"------------------------");
        return tbPersonJpa.getOne(id);
    }

    @Override
    public TbPerson addTbPersonId(TbPerson tbPerson) {
        return tbPersonJpa.save(tbPerson);
    }
}
