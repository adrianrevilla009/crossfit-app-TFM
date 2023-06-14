package adrian.tfm.crossfit.classes.infraestructure.rowMapper;

import adrian.tfm.crossfit.classes.domain.port.ClassExerciseUserDto;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class ClassExerciseUserRowMapper implements RowMapper<ClassExerciseUserDto> {
    @Override
    public ClassExerciseUserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClassExerciseUserDto classExerciseUserDto = new ClassExerciseUserDto();
        classExerciseUserDto.setClassId(rs.getLong("id"));
        classExerciseUserDto.setFull(rs.getBoolean("is_full"));
        classExerciseUserDto.setMaxPeople(rs.getInt("max_people"));
        classExerciseUserDto.setTime(rs.getObject("time", LocalDateTime.class));
        classExerciseUserDto.setRepetitions(rs.getInt("repetitions"));
        classExerciseUserDto.setSeries(rs.getInt("series"));
        classExerciseUserDto.setExerciseName(rs.getString("exercise_name"));
        classExerciseUserDto.setUserName(rs.getString("user_name"));
        classExerciseUserDto.setUserNif(rs.getString("nif"));
        return classExerciseUserDto;
    }
}
