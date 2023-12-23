package adrian.tfm.crossfit.classes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    //Creating Connection with Redis
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    //Creating RedisTemplate for Entity 'Session'
    @Bean
    public RedisTemplate<String, adrian.tfm.library.common.models.Session> redisTemplate(){
        RedisTemplate<String, adrian.tfm.library.common.models.Session> empTemplate = new RedisTemplate<>();
        empTemplate.setConnectionFactory(redisConnectionFactory());
        return empTemplate;
    }

    @Bean
    public adrian.tfm.library.common.dao.ISessionDao sessionDaoRepository() {
        return new adrian.tfm.library.common.dao.SessionDaoImpl();
    }
}
