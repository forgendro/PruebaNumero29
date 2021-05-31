package com.gfi.PruebaNumero29;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import servicebooster.arq.mcs.annotations.ONTConfig;





@ONTConfig
@EnableHystrix
@SpringBootApplication(scanBasePackages= {"com.gfi.PruebaNumero29","servicebooster.comp","servicebooster.arq"})
@EnableWebMvc
@EnableEurekaClient
@EnableCircuitBreaker
public class PruebaNumero29App {
	
	public static void main(String[] args) {
				
		SpringApplication.run(PruebaNumero29App.class, args);
	}



}
