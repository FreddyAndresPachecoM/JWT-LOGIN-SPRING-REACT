package com.m5a.autenticacion.autenticador;

import com.m5a.autenticacion.autenticador.config.AppProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class AutenticadorApplication {


	public static void main(String[] args) {
		SpringApplication.run(AutenticadorApplication.class, args);
	}

}
