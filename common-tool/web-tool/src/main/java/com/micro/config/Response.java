package com.micro.config;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.nio.file.Files;

public class Response {

    Request request;
    OutputStream output;
    MimetypesFileTypeMap mimeMap=new MimetypesFileTypeMap();

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        File file = new File(HttpServer.WEB_ROOT+request.getUri());
        if (file.exists()) {
            // 1、资源存在，读取资源
            try {
                StringBuffer result = new StringBuffer();
                result.append("HTTP /1.1 200 ok /r/n");
                result.append("Content-Type:"+mimeMap.getContentType(file)+" /r/n");
                result.append("Content-Length:" + file.length() + "/r/n");
                byte[]header=result.toString().getBytes();
                System.out.println(result.toString());
                BufferedInputStream in=new BufferedInputStream(new FileInputStream(file));
                int n=in.available();
                byte[] body = new byte[n];
                int allLength=header.length+n;
                byte[]all=new byte[allLength];
                System.arraycopy(header, 0, all, 0, header.length);
                System.arraycopy(body, 0, all, header.length, n);
                output.write(all);
                output.flush();
                output.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // 2、资源不存在，提示 file not found
            StringBuffer error = new StringBuffer();
            error.append("HTTP /1.1 400 file not found /r/n");
            error.append("Content-Type:text/html \r\n");
            error.append("Content-Length:20 \r\n").append("\r\n");
            error.append("<h1 >File Not Found..</h1>");
            try {
                output.write(error.toString().getBytes());
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            }
}