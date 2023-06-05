package adrian.tfm.crossfit.classes.infraestructure.repository;

import adrian.tfm.crossfit.classes.infraestructure.model.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseJpaRepository extends JpaRepository<ExerciseEntity, Long> {
}
