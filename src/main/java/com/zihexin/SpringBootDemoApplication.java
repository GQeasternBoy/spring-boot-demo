package com.zihexin;

import com.zihexin.state.Events;
import com.zihexin.state.States;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

@SpringBootApplication
public class SpringBootDemoApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}

	@Autowired
	private StateMachine<States,Events> stateMachine;

	@Override
	public void run(String... strings) throws Exception {

		stateMachine.start();
		stateMachine.sendEvent(Events.RECEIVE);
		stateMachine.sendEvent(Events.PAY);
	}


}
