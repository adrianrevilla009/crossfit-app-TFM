package adrian.tfm.crossfit.classes.infraestructure.repository;

import adrian.tfm.crossfit.classes.infraestructure.model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ClassJpaRepository extends JpaRepository<ClassEntity, Long> {
    @Query(value = "SELECT * FROM class_entity c WHERE c.time = ?1", nativeQuery = true)
    ClassEntity findByTime(LocalDateTime localDateTime);
}
