package adrian.tfm.crossfit.classes;

import adrian.tfm.crossfit.classes.domain.ClassUseCase;
import adrian.tfm.crossfit.classes.domain.ClassUseCaseImpl;
import adrian.tfm.crossfit.classes.domain.port.ClassRepository;
import adrian.tfm.crossfit.classes.domain.port.UserRepository;
import adrian.tfm.crossfit.classes.infraestructure.ClassRepositoryAdapter;
import adrian.tfm.crossfit.classes.infraestructure.ExerciseRepositoryAdapter;
import adrian.tfm.crossfit.classes.infraestructure.UserRepositoryAdapter;
import adrian.tfm.crossfit.classes.infraestructure.mapper.ClassDtoAndEntityMapper;
import adrian.tfm.crossfit.classes.infraestructure.mapper.UserDtoAndEntityMapper;
import adrian.tfm.crossfit.classes.infraestructure.repository.ClassJpaRepository;
import adrian.tfm.crossfit.classes.infraestructure.repository.ExerciseJpaRepository;
import adrian.tfm.crossfit.classes.infraestructure.repository.UserJpaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class ClassesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassesApplication.class, args);
	}

	@Bean
	public Mapper mapper() {
		return new DozerBeanMapper(Arrays.asList("dozer_mapping.xml"));
	}

	@Bean
	public ClassRepositoryAdapter classRepository(ClassJpaRepository classJpaRepository, Mapper mapper,
												  ClassDtoAndEntityMapper classDtoAndEntityMapper,
												  UserDtoAndEntityMapper userDtoAndEntityMapper,
												  UserJpaRepository userJpaRepository) {
		return new ClassRepositoryAdapter(classJpaRepository, mapper, classDtoAndEntityMapper, userDtoAndEntityMapper,
				userJpaRepository);
	}

	@Bean
	public ExerciseRepositoryAdapter exerciseRepository(ExerciseJpaRepository exerciseJpaRepository, Mapper mapper) {
		return new ExerciseRepositoryAdapter(exerciseJpaRepository, mapper);
	}

	@Bean
	public UserRepositoryAdapter userRepository(UserJpaRepository userJpaRepository, UserDtoAndEntityMapper userDtoAndEntityMapper) {
		return new UserRepositoryAdapter(userJpaRepository, userDtoAndEntityMapper);
	}

	@Bean
	public ClassUseCase classUseCase(ClassRepository classRepository, UserRepository userRepository) {
		return new ClassUseCaseImpl(classRepository, userRepository);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		return objectMapper;
	}

}
