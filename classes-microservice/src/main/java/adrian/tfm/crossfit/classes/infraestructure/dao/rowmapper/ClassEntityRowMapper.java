package adrian.tfm.crossfit.classes.infraestructure.dao.rowmapper;

import adrian.tfm.crossfit.classes.infraestructure.model.ClassEntity;
import adrian.tfm.crossfit.classes.infraestructure.model.ExerciseEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// TODO delete
public class ClassEntityRowMapper implements RowMapper<ClassEntity> {
    @Override
    public ClassEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(rs.getLong("class_id"));
        classEntity.setName(rs.getString("class_name"));
        classEntity.setFull(rs.getBoolean("is_full"));
        // classEntity.setTime(rs.getDate("time"));
        ExerciseEntity exerciseEntity = new ExerciseEntity();
        exerciseEntity.setId(rs.getLong("exercise_id"));
        exerciseEntity.setName(rs.getString("exercise_name"));

        return classEntity;
    }
}
