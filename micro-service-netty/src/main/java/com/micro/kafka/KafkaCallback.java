package com.micro.kafka;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.kafka
 * Author:   hhc
 * Date:     2019/5/31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class KafkaCallback implements ListenableFutureCallback<SendResult<Integer, String>> {

    @Override
    public void onFailure(Throwable throwable) {

    }

    @Override
    public void onSuccess(SendResult<Integer, String> integerStringSendResult) {

    }
}
