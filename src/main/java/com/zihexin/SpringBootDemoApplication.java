package com.zihexin;

import com.zihexin.config.JpaConfiguration;
import com.zihexin.state.Events;
import com.zihexin.state.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@ImportAutoConfiguration(value = JpaConfiguration.class)
public class SpringBootDemoApplication{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

	private static Logger logger = LoggerFactory.getLogger(SpringBootDemoApplication.class);

	@RequestMapping(value = "/test",method = RequestMethod.POST)
	public String test(){
		logger.info("logger level : INFO");
		logger.error("logger lever : ERROR");
		logger.debug("logger lever : DEBUG");
		return "success";
	}


}
