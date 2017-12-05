package com.javainuse;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.google.gson.Gson;
import com.netflix.discovery.shared.Application;

//import StockTest.StockPrice;


@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootHelloWorldApplication{
	public static void main(String[] args) {
		SpringApplication.run(SpringBootHelloWorldApplication.class, args);
	 	
		}
		
}	
	


