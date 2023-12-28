package adrian.tfm.crossfit.classes.domain.mapper;

import adrian.tfm.crossfit.classes.domain.port.ClassExerciseUserDto;
import adrian.tfm.crossfit.classes.dto.response.ClassExerciseUserResponse;
import org.springframework.stereotype.Component;

@Component
public class ClassExerciseUserResponseAndDtoMapper {
    public ClassExerciseUserResponse fromClassDtoToResponse(ClassExerciseUserDto classExerciseUserDto) {
        ClassExerciseUserResponse cl = new ClassExerciseUserResponse();
        cl.setClassId(classExerciseUserDto.getClassId());
        cl.setFull(classExerciseUserDto.getFull());
        cl.setRepetitions(classExerciseUserDto.getRepetitions());
        cl.setExerciseName(classExerciseUserDto.getExerciseName());
        cl.setSeries(classExerciseUserDto.getSeries());
        cl.setTime(classExerciseUserDto.getTime());
        cl.setMaxPeople(classExerciseUserDto.getMaxPeople());
        cl.setUserName(classExerciseUserDto.getUserName());
        cl.setUserNif(classExerciseUserDto.getUserNif());
        return cl;
    }
}
