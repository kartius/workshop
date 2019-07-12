package org.hys.workshop.statemachine.shell;

import lombok.extern.slf4j.Slf4j;
import org.hys.workshop.statemachine.persister.RedisRuntimePersister;
import org.hys.workshop.statemachine.statemachine.PaymentStateMachineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@ShellComponent
@Component
@Slf4j
public class StateMachineCommands {

    @Autowired
    private RedisRuntimePersister persister;

    @Autowired
    private StateMachineFactory<PaymentStateMachineFactory.States, PaymentStateMachineFactory.Events> factory;
    private StateMachine<PaymentStateMachineFactory.States, PaymentStateMachineFactory.Events> stateMachine;

    @PostConstruct
    public void init() {
        String machineId = "123";
        stateMachine = factory.getStateMachine(machineId);
        stateMachine.getExtendedState().getVariables().put("status", "registered");
        restore(machineId);
        log.info(String.format("Extended state variables - %s", stateMachine.getExtendedState().getVariables()));
    }

//    private int getRundomId(){
//        int min =1;
//        int max =100;
//        return ThreadLocalRandom.current().nextInt(min, max + 1);
//    }

    private void restore(String machineId) {
        final StateMachineContext<PaymentStateMachineFactory.States, PaymentStateMachineFactory.Events> context = persister.read(machineId);
        if (context != null) {
            stateMachine.stop();
            stateMachine.getStateMachineAccessor().doWithAllRegions(function -> function.resetStateMachine(context));
            stateMachine.start();
            log.info(stateMachine.getState().getId().name());
        }
    }


    @ShellMethod(value = "event", key = "sendEvent")
    public void sendEvent(PaymentStateMachineFactory.Events event) {
        stateMachine.getExtendedState().getVariables().putIfAbsent(event.name(), Instant.now().toString());
        stateMachine.sendEvent(event);
    }

}