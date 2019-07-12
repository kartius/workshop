package org.hys.workshop.statemachine.persister;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.data.redis.RedisStateMachineContextRepository;

public class RedisRepository extends RedisStateMachineContextRepository {
    /**
     * Instantiates a new redis state machine context repository.
     *
     * @param redisConnectionFactory the redis connection factory
     */
    public RedisRepository(RedisConnectionFactory redisConnectionFactory) {
        super(redisConnectionFactory);
    }
}
