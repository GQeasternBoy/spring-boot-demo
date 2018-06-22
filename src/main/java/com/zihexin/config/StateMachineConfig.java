package com.zihexin.config;

import com.zihexin.state.Events;
import com.zihexin.state.States;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

import java.util.EnumSet;

/**
 * Created by Administrator on 2018/6/19.
 */
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States,Events>{

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates().initial(States.UNPAID).states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal().source(States.UNPAID).target(States.WAITING_FOR_RECEIVE).event(Events.PAY)
                .and().withExternal().source(States.WAITING_FOR_RECEIVE).target(States.DONE).event(Events.RECEIVE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration().listener(listener());
    }

    @Bean
    public StateMachineListener<States,Events> listener(){
        return new StateMachineListenerAdapter<States,Events>(){

          @Override
          public void transition(Transition<States,Events> transition) {
                if (transition.getTarget().getId() == States.UNPAID){
                    LOGGER.info("订单创建，待支付");
                    return;
                }
                if (transition.getSource().getId() == States.UNPAID &&
                        transition.getTarget().getId() == States.WAITING_FOR_RECEIVE){
                    LOGGER.info("订单支付，待收货");
                    return;
                }
                if(transition.getSource().getId() == States.WAITING_FOR_RECEIVE &&
                        transition.getTarget().getId() == States.DONE){
                    LOGGER.info("订单完成");
                    return;
                }
          }

        };
    }

}