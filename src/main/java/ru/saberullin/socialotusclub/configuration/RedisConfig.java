package ru.saberullin.socialotusclub.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
@EnableMBeanExport(registration= RegistrationPolicy.REPLACE_EXISTING)
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String redisHost;
    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public JedisPool jedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setJmxEnabled(false);
        return new JedisPool(config, redisHost, redisPort);
    }
}
