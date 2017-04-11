package com.ham;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
//@EnableAutoConfiguration
@MapperScan("com.ham.dao")
public class SearchFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchFoodApplication.class, args);
	}
}
