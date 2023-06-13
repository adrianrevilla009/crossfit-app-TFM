package adrian.tfm.crossfit.classes.infraestructure.repository;

import adrian.tfm.crossfit.classes.infraestructure.model.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassJpaRepository extends JpaRepository<ClassEntity, Long> {
    @Query(value = "SELECT * FROM class_entity c WHERE c.time = ?1", nativeQuery = true)
    ClassEntity findByTime(LocalDateTime localDateTime);

    // TODO aqui hay que hacer un DAO con un mapper
    @Query(value = "select ce.id as id, ce.is_full as is_full, ce.max_people as max_people, ce.`time` as time, " +
            "efce.repetitions as repetitions, efce.series as series, ee.name as exercise_name, ue.name as user_name, ue.nif as nif " +
            "from class_entity ce " +
            "inner join exercise_for_class_entity efce on ce.id = efce.class_entity_id " +
            "inner join exercise_entity ee on efce.exercise_id = ee.id " +
            "inner join user_entity ue on ce.id = ue.class_entity_id " +
            "where ue.nif = ?1", nativeQuery = true)
    List<ClassEntity> findByUserNif(String nif);
}
