package com.shev.itembank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ItemBankServiceApplication
{

	public static void main(String[] args)
	{
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(ItemBankServiceApplication.class, args);
	}

}
