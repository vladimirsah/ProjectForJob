package com.example.projectforjob.config;

import com.example.projectforjob.models.Comment;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@EnableCaching
public class RedisConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Comment> commentRedisTemplate() {
        RedisTemplate<String, Comment> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        template.setKeySerializer(new StringRedisSerializer());
        Jackson2JsonRedisSerializer<Comment> value = new Jackson2JsonRedisSerializer<>(Comment.class);
        template.setValueSerializer(value);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(value);
        template.afterPropertiesSet();

        return template;
    }


}
