package com.example.ws13.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Bean
    public RedisTemplate<String, Object> RedisTemplate() {

        // Redis config
        final RedisStandaloneConfiguration config = new  RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(Integer.parseInt(redisPort));
        System.out.println(redisHost);
        System.out.println(redisPort);

        // Jedis config
        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();

        // Redis-Jedis connection
        final JedisConnectionFactory jedisFactory = new JedisConnectionFactory(config, jedisClient);
        jedisFactory.afterPropertiesSet();

        // Redis Template
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisFactory);

        // Template Serialization
        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer(getClass().getClassLoader());

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());

        return template;

    }
    
}
