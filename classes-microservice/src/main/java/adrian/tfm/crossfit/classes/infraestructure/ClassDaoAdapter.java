package adrian.tfm.crossfit.classes.infraestructure;

import adrian.tfm.crossfit.classes.domain.port.ClassDao;
import adrian.tfm.crossfit.classes.domain.port.ClassExerciseUserDto;
import adrian.tfm.crossfit.classes.infraestructure.rowMapper.ClassExerciseUserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ClassDaoAdapter implements ClassDao {
    private JdbcTemplate jdbcTemplate;

    public ClassDaoAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ClassExerciseUserDto> getClassesByUser(String nif) {
        String query = "select ce.id as id, ce.is_full as is_full, ce.max_people as max_people, ce.`time` as time, " +
                "efce.repetitions as repetitions, efce.series as series, ee.name as exercise_name, ue.name as user_name, ue.nif as nif " +
                "from class_entity ce " +
                "inner join exercise_for_class_entity efce on ce.id = efce.class_entity_id " +
                "inner join exercise_entity ee on efce.exercise_id = ee.id " +
                "inner join user_entity ue on ce.id = ue.class_entity_id " +
                "where ue.nif = " + nif;
        return jdbcTemplate.query(query, new ClassExerciseUserRowMapper());
    }


}
