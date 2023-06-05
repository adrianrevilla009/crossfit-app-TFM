package adrian.tfm.crossfit.classes.domain.port;

import java.util.List;

public interface ExerciseRepository {
    void saveExerciseList(List<ExerciseDto> exerciseDtoList);
}
