package com.micro.controller;

import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import java.io.*;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.controller
 * Author:   hhc
 * Date:     2019/6/12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class Test {

    public static void main(String[] args) {
//        XWPFTemplate template = XWPFTemplate.compile("C:\\Users\\lenovo\\Desktop\\xxx\\poi_tl.docx").render(new HashMap<String, Object>(){{
//            put("title", "侯华成");
//        }});
//        FileOutputStream out = null;
//        try {
//            out = new FileOutputStream("C:\\Users\\lenovo\\Desktop\\xxx\\2.docx");
//            template.write(out);
//            out.flush();
//            out.close();
//            template.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try {
            word07ToHtml("C:/Users/lenovo/Desktop/xxx/poi_tl.docx","C:/Users/lenovo/Desktop/xxx/1.png","C:/Users/lenovo/Desktop/xxx/1.html");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public static void word07ToHtml(String fileName ,String imageFile , String htmFile) throws IOException{
        File f = new File(fileName);
        if (!f.exists()) {
            System.out.println("sorry file does not exists");
        }else{
            if (f.getName().endsWith(".docx")|| f.getName().endsWith(".DOCX") || f.getName().endsWith(".doc")) {
                //1:加载文档到XWPFDocument
                InputStream in = new FileInputStream(f);
                XWPFDocument document = new XWPFDocument(in);
                //2：加载图片到指定文件夹
                File imgFile = new File(imageFile);
                XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imgFile));
                options.setExtractor(new FileImageExtractor(imgFile));

                //3：转换XWPFDocument to XHTML
                OutputStream out = new FileOutputStream(new File(htmFile));
                XHTMLConverter.getInstance().convert(document, out, null);
            }else{
                System.out.println("Enter only MS Office 2007+ files");
            }
        }
    }
}
