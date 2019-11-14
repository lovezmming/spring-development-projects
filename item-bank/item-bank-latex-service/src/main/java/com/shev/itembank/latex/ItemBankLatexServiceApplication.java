package com.shev.itembank.latex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan({"com.shev.itembank.**.mapper", "com.shev.itembank.**.custom"})
@ComponentScan({"com.shev.itembank.*"})
@SpringBootApplication
public class ItemBankLatexServiceApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(ItemBankLatexServiceApplication.class, args);
	}

}
