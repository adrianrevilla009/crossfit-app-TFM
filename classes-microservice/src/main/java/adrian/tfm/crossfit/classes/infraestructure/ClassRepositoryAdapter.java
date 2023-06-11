package adrian.tfm.crossfit.classes.infraestructure;

import adrian.tfm.crossfit.classes.domain.port.*;
import adrian.tfm.crossfit.classes.infraestructure.model.ClassEntity;
import adrian.tfm.crossfit.classes.infraestructure.model.ExerciseEntity;
import adrian.tfm.crossfit.classes.infraestructure.model.ExerciseForClassEntity;
import adrian.tfm.crossfit.classes.infraestructure.model.UserEntity;
import adrian.tfm.crossfit.classes.infraestructure.repository.ClassJpaRepository;
import org.dozer.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassRepositoryAdapter implements ClassRepository {

    private ClassJpaRepository classJpaRepository;
    private Mapper mapper;

    public ClassRepositoryAdapter(ClassJpaRepository classJpaRepository, Mapper mapper) {
        this.classJpaRepository = classJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void saveClassList(List<ClassDto> classDtoList) {
        List<ClassEntity> classEntityList = new ArrayList<>();
        for (ClassDto classDto : classDtoList) {
            ClassEntity classEntity = this.mapFromClassDtoToClassEntity(classDto);
            classEntityList.add(classEntity);
        }

        this.classJpaRepository.saveAll(classEntityList);
    }


    private ClassEntity mapFromClassDtoToClassEntity(ClassDto classDto) {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(classDto.getId());
        classEntity.setName(classDto.getName());
        classEntity.setTime(classDto.getTime());
        classEntity.setFull(classDto.getFull());

        List<ExerciseForClassEntity> exerciseForClassEntities = new ArrayList<>();
        for (ExerciseForClassDto exercise : classDto.getExerciseForClassDtoList()) {
            ExerciseForClassEntity exerciseForClassEntity = new ExerciseForClassEntity();
            exerciseForClassEntity.setId(exercise.getId());
            exerciseForClassEntity.setExercise(new ExerciseEntity(
                    exercise.getExercise().getId(),
                    exercise.getExercise().getName()
            ));
            exerciseForClassEntity.setRepetitions(exercise.getRepetitions());
            exerciseForClassEntity.setSeries(exercise.getSeries());
            exerciseForClassEntity.setClassEntity(classEntity);
            exerciseForClassEntities.add(exerciseForClassEntity);
        }
        classEntity.setExerciseForClassEntityList(exerciseForClassEntities);

        List<UserEntity> userEntities = new ArrayList<>();
        for (UserDto user : classDto.getUserList()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(user.getId());
            userEntity.setName(user.getName());
            userEntity.setClassEntity(classEntity);
            userEntities.add(userEntity);
        }
        classEntity.setUserList(userEntities);

        return classEntity;
    }

    private ClassDto mapFromClassEntityToClassDto(ClassEntity classEntity) {
        ClassDto classDto = new ClassDto();
        classDto.setId(classEntity.getId());
        classDto.setName(classEntity.getName());
        classDto.setTime(classEntity.getTime());
        classDto.setFull(classEntity.getFull());

        List<ExerciseForClassDto> exerciseForClassDtoList = new ArrayList<>();
        for (ExerciseForClassEntity exercise : classEntity.getExerciseForClassEntityList()) {
            ExerciseForClassDto exerciseForClassDto = new ExerciseForClassDto();
            exerciseForClassDto.setId(exercise.getId());
            exerciseForClassDto.setExercise(new ExerciseDto(
                    exercise.getExercise().getId(),
                    exercise.getExercise().getName()
            ));
            exerciseForClassDto.setRepetitions(exercise.getRepetitions());
            exerciseForClassDto.setSeries(exercise.getSeries());
            exerciseForClassDtoList.add(exerciseForClassDto);
        }
        classDto.setExerciseForClassDtoList(exerciseForClassDtoList);

        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity user : classEntity.getUserList()) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDtoList.add(userDto);
        }
        classDto.setUserList(userDtoList);

        return classDto;
    }

    public List<ClassDto> getAllClasses() {
        List<ClassEntity> classEntityList = this.classJpaRepository.findAll();
        List<ClassDto> classDtoList = new ArrayList<>();
        for (ClassEntity classEntity : classEntityList) {
            ClassDto classDto = this.mapFromClassEntityToClassDto(classEntity);
            classDtoList.add(classDto);
        }
        return classDtoList;
    }
}
