package com.ecnu.hmg.hmguserservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisTemplateUtil {

    @Bean
    public <K,V> RedisTemplate<K,V> redisSessionTemplate(RedisConnectionFactory factory){
        RedisTemplate<K,V> template=new RedisTemplate<>();
        //配置连接工厂
        template.setConnectionFactory(factory);
        RedisSerializer<String> keySerializer=new StringRedisSerializer();
        RedisSerializer<Object> valueSerializer=new JdkSerializationRedisSerializer(
                this.getClass().getClassLoader()
        );
        template.setValueSerializer(valueSerializer);
        template.setKeySerializer(keySerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
