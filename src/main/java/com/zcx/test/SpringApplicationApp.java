package com.zcx.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.zcx.test.dao")
public class SpringApplicationApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringApplicationApp.class, args);
	}

}
