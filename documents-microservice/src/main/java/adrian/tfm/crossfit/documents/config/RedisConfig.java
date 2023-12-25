package adrian.tfm.crossfit.documents.config;

import adrian.tfm.crossfit.documents.commons.dao.ISessionDao;
import adrian.tfm.crossfit.documents.commons.dao.SessionDaoImpl;
import adrian.tfm.crossfit.documents.commons.models.Session;
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
    public RedisTemplate<String, Session> redisTemplate(){
        RedisTemplate<String, Session> empTemplate = new RedisTemplate<>();
        empTemplate.setConnectionFactory(redisConnectionFactory());
        return empTemplate;
    }

    @Bean
    public ISessionDao sessionDaoRepository() {
        return new SessionDaoImpl();
    }
}
