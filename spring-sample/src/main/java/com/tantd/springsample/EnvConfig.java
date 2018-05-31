package com.tantd.springsample;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class EnvConfig {

	protected static Log log = LogFactory.getLog(EnvConfig.class);
	private static Environment env = null;

	/**
	 * @return the env
	 */
	public static Environment getEnv() {
		return env;
	}

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
	}

	/***************************************************
	 *
	 * @param e
	 */
	@Autowired
	public static void setEnvironment(Environment e) {
		env = e;
	}

}