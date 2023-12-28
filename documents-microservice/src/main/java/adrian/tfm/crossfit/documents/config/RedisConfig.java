package adrian.tfm.crossfit.documents.config;

import adrian.tfm.crossfit.documents.commons.dao.ISessionDao;
import adrian.tfm.crossfit.documents.commons.dao.SessionDaoImpl;
import adrian.tfm.crossfit.documents.commons.models.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    //Creating Connection with Redis
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        logger.info("REDIS Host: " + host);
        logger.info("REDIS Port: " + port);

        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.defaultConfiguration();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
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
