package adrian.tfm.crossfit.classes.domain.port;

import java.util.List;

public interface ClassDao {
    List<ClassExerciseUserDto> getClassesByUser(String nif);
}
