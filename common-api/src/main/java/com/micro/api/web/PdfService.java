package com.micro.api.web;

import com.micro.api.web.model.request.PdfModel;
import com.micro.response.global.BaseResponse;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.web
 * Author:   hhc
 * Date:     2019/3/6
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public interface PdfService {
    BaseResponse uploadPdf(PdfModel pdfModel);
    BaseResponse updatePdf(PdfModel pdfModel);
}
