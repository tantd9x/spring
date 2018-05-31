package com.tantd.springsample;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Bootstrap {

	private static final Log log = LogFactory.getLog(Bootstrap.class);
	public static ConfigurableApplicationContext APP_CONTEXT;

	public static void main(String[] args) throws InterruptedException, SQLException {
		APP_CONTEXT = SpringApplication.run(Bootstrap.class, args);
		log.info("APP CONTEXT LOADED => " + APP_CONTEXT);

	}

	@Autowired
	void setEnvironment(Environment e) {
		log.info("Setting environment...");
		EnvConfig.setEnvironment(e);
	}

}
