package adrian.tfm.crossfit.security;

import adrian.tfm.crossfit.security.mapper.UserMapper;
import adrian.tfm.crossfit.security.repository.UserRepository;
import adrian.tfm.crossfit.security.service.UserService;
import adrian.tfm.crossfit.security.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class SecurityApplication {

	public static void main(String[] args) {
    SpringApplication.run(SecurityApplication.class, args);
	}



}
