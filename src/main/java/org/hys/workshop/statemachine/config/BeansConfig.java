package org.hys.workshop.statemachine.config;


import lombok.Getter;
import lombok.Setter;
import org.hys.workshop.statemachine.persister.RedisRepository;
import org.hys.workshop.statemachine.persister.RedisRuntimePersister;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@Setter
@Getter
public class BeansConfig {

    @Value("${redis.url}")
    private String redisUrl;
    @Value("${redis.port}")
    private String redisPort;
    @Value("${redis.expiration}")
    private String redisExpiration;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisUrl, Integer.valueOf(redisPort));
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
        return redisConnectionFactory;
    }

    @Bean
    RedisRepository redisRepo() {
        return new RedisRepository(redisConnectionFactory());
    }

    @Bean
    RedisRuntimePersister redisRuntimePersister(){
        return new RedisRuntimePersister();
    }
}
