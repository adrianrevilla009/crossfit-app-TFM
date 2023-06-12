package adrian.tfm.crossfit.classes.domain.mapper;

import adrian.tfm.crossfit.classes.domain.port.ClassDto;
import adrian.tfm.crossfit.classes.domain.port.ExerciseDto;
import adrian.tfm.crossfit.classes.domain.port.ExerciseForClassDto;
import adrian.tfm.crossfit.classes.domain.port.UserDto;
import adrian.tfm.crossfit.classes.dto.request.ClassRequest;
import adrian.tfm.crossfit.classes.dto.request.ExerciseForClassRequest;
import adrian.tfm.crossfit.classes.dto.request.UserRequest;
import adrian.tfm.crossfit.classes.dto.response.ExerciseForClassResponse;
import adrian.tfm.crossfit.classes.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassRequestAndDtoMapper {
    public ClassDto fromClassRequestToDto(ClassRequest classRequest) {
        ClassDto classDto = new ClassDto();
        classDto.setId(classRequest.getId());
        classDto.setName(classRequest.getName());
        classDto.setTime(classRequest.getTime());
        classDto.setFull(classRequest.getFull());

        List<ExerciseForClassDto> exerciseForClassDtoList = new ArrayList<>();
        if (classRequest.getExerciseForClassDtoList() != null) {
            for (ExerciseForClassRequest exercise : classRequest.getExerciseForClassDtoList()) {
                ExerciseForClassDto exerciseForClassDto = new ExerciseForClassDto();
                exerciseForClassDto.setExercise(new ExerciseDto(
                        exercise.getExercise().getName()
                ));
                exerciseForClassDto.setRepetitions(exercise.getRepetitions());
                exerciseForClassDto.setSeries(exercise.getSeries());
                exerciseForClassDto.setMeters(exercise.getMeters());
                exerciseForClassDtoList.add(exerciseForClassDto);
            }
        }
        classDto.setExerciseForClassDtoList(exerciseForClassDtoList);

        List<UserDto> userDtoList = new ArrayList<>();
        if (classRequest.getUserList() != null) {
            for (UserRequest userRequest : classRequest.getUserList()) {
                UserDto userDto = new UserDto();
                userDto.setName(userRequest.getName());
                userDtoList.add(userDto);
            }
        }
        classDto.setUserList(userDtoList);

        return classDto;
    }
}
