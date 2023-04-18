package com.vineria.vineria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VineriaApplication {
	final static Logger logger = LoggerFactory.getLogger("VineriaApplication");

	public static void main(String[] args) {
		SpringApplication.run(VineriaApplication.class, args);
		logger.info("##############################");
		logger.info("####### ANDANDO CHAVAL #######");
		logger.info("##############################");
	}

}