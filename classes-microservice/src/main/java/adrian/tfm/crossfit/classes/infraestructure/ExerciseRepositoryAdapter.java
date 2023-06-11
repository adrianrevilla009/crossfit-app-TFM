package adrian.tfm.crossfit.classes.infraestructure;

import adrian.tfm.crossfit.classes.domain.port.ExerciseDto;
import adrian.tfm.crossfit.classes.domain.port.ExerciseRepository;
import adrian.tfm.crossfit.classes.infraestructure.model.ExerciseEntity;
import adrian.tfm.crossfit.classes.infraestructure.repository.ExerciseJpaRepository;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRepositoryAdapter implements ExerciseRepository {
    private ExerciseJpaRepository exerciseJpaRepository;
    private Mapper mapper;

    public ExerciseRepositoryAdapter(ExerciseJpaRepository exerciseJpaRepository, Mapper mapper) {
        this.exerciseJpaRepository = exerciseJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void saveExerciseList(List<ExerciseDto> exerciseDtoList) {
        List<ExerciseEntity> exerciseEntityList = new ArrayList<>();
        for (ExerciseDto exerciseDto : exerciseDtoList) {
            ExerciseEntity exerciseEntity = this.mapper.map(exerciseDto, ExerciseEntity.class);
            exerciseEntityList.add(exerciseEntity);
        }

        this.exerciseJpaRepository.saveAll(exerciseEntityList);
    }
}
