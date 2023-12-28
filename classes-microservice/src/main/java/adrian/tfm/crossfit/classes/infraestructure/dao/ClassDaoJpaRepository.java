package adrian.tfm.crossfit.classes.infraestructure.dao;

import adrian.tfm.crossfit.classes.infraestructure.dao.rowmapper.ClassEntityRowMapper;
import adrian.tfm.crossfit.classes.infraestructure.model.ClassEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

// TODO delete
@Repository
public class ClassDaoJpaRepository {
    private final JdbcTemplate jdbcTemplate;

    public ClassDaoJpaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ClassEntity> findByUserNif(String nif) {
        String sql = "select ce.id as class_id, ce.class_name as class_name, ce.is_full as is_full, ce.`time` as time, " +
                "efce.id as exercise_class_id, efce.repetitions as repetitions, efce.series as series, " +
                "ee.id as exercise_id, ee.exercise_name as exercise_name, " +
                "ue.id as user_id, ue.user_name as user_name, ue.nif as nif " +
                "from class_entity ce " +
                "inner join exercise_for_class_entity efce on ce.id = efce.class_entity_id " +
                "inner join exercise_entity ee on efce.exercise_id = ee.id " +
                "inner join user_entity ue on ce.id = ue.class_entity_id " +
                "where ue.nif = " + nif;
        return jdbcTemplate.query(sql, new ClassEntityRowMapper());
    }

}
