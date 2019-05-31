package com.micro;


//import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.fastjson.JSONObject;
import com.micro.model.TbDepartment;
import com.micro.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.servlet.FilterChain;
import javax.servlet.Servlet;

//@EnableDubbo
@SpringBootApplication
@EnableFeignClients
public class DroolsApplication implements CommandLineRunner {

	@Autowired
	Test test;
	public static void main(String[] args) {
		SpringApplication.run(DroolsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		TbDepartment tbDepartment=new TbDepartment();
		JSONObject j=new JSONObject();
		j.put("pageNum",1);
		j.put("pageSize",2);
		JSONObject o=test.getEngineMesasge("123",j,"234");
		System.out.println(o.toJSONString());
	}
}
