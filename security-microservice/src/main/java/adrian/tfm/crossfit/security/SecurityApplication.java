package adrian.tfm.crossfit.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackages = "adrian.tfm.crossfit.security.commons.dao")
public class SecurityApplication {

	public static void main(String[] args) {
    SpringApplication.run(SecurityApplication.class, args);
	}



}
