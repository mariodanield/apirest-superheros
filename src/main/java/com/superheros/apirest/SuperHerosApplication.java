package com.superheros.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

//Configuración spring boot y archivo properties
@SpringBootApplication
@PropertySource("classpath:config/superHero.properties")
public class SuperHerosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperHerosApplication.class, args);
	}

}
