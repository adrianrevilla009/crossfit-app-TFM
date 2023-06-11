package adrian.tfm.crossfit.classes;

import adrian.tfm.crossfit.classes.domain.ClassUseCase;
import adrian.tfm.crossfit.classes.domain.ClassUseCaseImpl;
import adrian.tfm.crossfit.classes.domain.port.ClassRepository;
import adrian.tfm.crossfit.classes.infraestructure.ClassRepositoryAdapter;
import adrian.tfm.crossfit.classes.infraestructure.ExerciseRepositoryAdapter;
import adrian.tfm.crossfit.classes.infraestructure.repository.ClassJpaRepository;
import adrian.tfm.crossfit.classes.infraestructure.repository.ExerciseJpaRepository;
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
	public ClassRepositoryAdapter classRepository(ClassJpaRepository classJpaRepository, Mapper mapper) {
		return new ClassRepositoryAdapter(classJpaRepository, mapper);
	}

	@Bean
	public ExerciseRepositoryAdapter exerciseRepository(ExerciseJpaRepository exerciseJpaRepository, Mapper mapper) {
		return new ExerciseRepositoryAdapter(exerciseJpaRepository, mapper);
	}

	@Bean
	public ClassUseCase classUseCase(ClassRepository classRepository) {
		return new ClassUseCaseImpl(classRepository);
	}



}
