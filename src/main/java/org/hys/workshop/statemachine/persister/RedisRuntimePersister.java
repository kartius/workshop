package org.hys.workshop.statemachine.persister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.persist.AbstractPersistingStateMachineInterceptor;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptor;
import org.springframework.statemachine.transition.Transition;
import org.springframework.statemachine.transition.TransitionKind;

public class RedisRuntimePersister<S, E, T> extends AbstractPersistingStateMachineInterceptor<S, E, T>
        implements StateMachineRuntimePersister<S, E, T> {

    @Autowired
    private RedisRepository redisRepository;

    @Override
    public StateMachineInterceptor<S, E> getInterceptor() {
        return this;
    }

    @Override
    public void write(StateMachineContext<S, E> context, T contextObj) {
        redisRepository.save(context, (String) contextObj);
    }

    @Override
    public StateMachineContext<S, E> read(T contextObj) {
        return redisRepository.getContext((String) contextObj);
    }

    @Override
    public void postStateChange(State<S, E> state, Message<E> message, Transition<S, E> transition, StateMachine<S, E> stateMachine) {
        if (!transition.getKind().equals(TransitionKind.INITIAL)) {
            super.postStateChange(state, message, transition, stateMachine);
        }
    }
}
