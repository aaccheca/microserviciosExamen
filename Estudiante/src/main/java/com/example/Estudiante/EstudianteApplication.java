package com.example.Estudiante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EstudianteApplication {
	public static void main(String[] args) {
		SpringApplication.run(EstudianteApplication.class, args);
	}
}
