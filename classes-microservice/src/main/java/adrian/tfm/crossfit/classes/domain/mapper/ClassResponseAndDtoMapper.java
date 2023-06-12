package adrian.tfm.crossfit.classes.domain.mapper;

import adrian.tfm.crossfit.classes.domain.port.ClassDto;
import adrian.tfm.crossfit.classes.domain.port.ExerciseDto;
import adrian.tfm.crossfit.classes.domain.port.ExerciseForClassDto;
import adrian.tfm.crossfit.classes.domain.port.UserDto;
import adrian.tfm.crossfit.classes.dto.response.ClassResponse;
import adrian.tfm.crossfit.classes.dto.response.ExerciseForClassResponse;
import adrian.tfm.crossfit.classes.dto.response.ExerciseResponse;
import adrian.tfm.crossfit.classes.dto.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassResponseAndDtoMapper {
    public ClassDto fromClassResponseToDto(ClassResponse classResponse) {
        ClassDto classDto = new ClassDto();
        classDto.setName(classResponse.getName());
        classDto.setTime(classResponse.getTime());
        classDto.setFull(classResponse.getFull());

        List<ExerciseForClassDto> exerciseForClassDtoList = new ArrayList<>();
        for (ExerciseForClassResponse exercise : classResponse.getExerciseForClassDtoList()) {
            ExerciseForClassDto exerciseForClassDto = new ExerciseForClassDto();
            exerciseForClassDto.setExercise(new ExerciseDto(
                    exercise.getExercise().getName()
            ));
            exerciseForClassDto.setRepetitions(exercise.getRepetitions());
            exerciseForClassDto.setSeries(exercise.getSeries());
            exerciseForClassDto.setMeters(exercise.getMeters());
            exerciseForClassDtoList.add(exerciseForClassDto);
        }
        classDto.setExerciseForClassDtoList(exerciseForClassDtoList);

        List<UserDto> userDtoList = new ArrayList<>();
        for (UserResponse userResponse : classResponse.getUserList()) {
            UserDto userDto = new UserDto();
            userDto.setName(userResponse.getName());
            userDtoList.add(userDto);
        }
        classDto.setUserList(userDtoList);

        return classDto;
    }

    public ClassResponse fromClassDtoToResponse(ClassDto classDto) {
        ClassResponse classResponse = new ClassResponse();
        classResponse.setName(classDto.getName());
        classResponse.setTime(classDto.getTime());
        classResponse.setFull(classDto.getFull());

        List<ExerciseForClassResponse> exerciseForClassResponseList = new ArrayList<>();
        for (ExerciseForClassDto exercise : classDto.getExerciseForClassDtoList()) {
            ExerciseForClassResponse exerciseForClassResponse = new ExerciseForClassResponse();
            exerciseForClassResponse.setExercise(new ExerciseResponse(
                    exercise.getExercise().getName()
            ));
            exerciseForClassResponse.setRepetitions(exercise.getRepetitions());
            exerciseForClassResponse.setSeries(exercise.getSeries());
            exerciseForClassResponse.setMeters(exercise.getMeters());
            exerciseForClassResponseList.add(exerciseForClassResponse);
        }
        classResponse.setExerciseForClassDtoList(exerciseForClassResponseList);

        List<UserResponse> userResponseList = new ArrayList<>();
        for (UserDto userDto : classDto.getUserList()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setName(userDto.getName());
            userResponseList.add(userResponse);
        }
        classResponse.setUserList(userResponseList);

        return classResponse;
    }
}
