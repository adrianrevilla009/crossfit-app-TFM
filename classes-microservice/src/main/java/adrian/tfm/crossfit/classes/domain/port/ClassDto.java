package adrian.tfm.crossfit.classes.domain.port;

import java.time.LocalDateTime;
import java.util.List;

public class ClassDto {
    private Long id;
    private String name;
    private LocalDateTime time;

    private List<UserDto> userList;

    private Boolean isFull;
    private List<ExerciseForClassDto> exerciseForClassDtoList;

    public ClassDto(String name, LocalDateTime time, List<UserDto> userList, Boolean isFull, List<ExerciseForClassDto> exerciseForClassDtoList) {
        this.name = name;
        this.time = time;
        this.userList = userList;
        this.isFull = isFull;
        this.exerciseForClassDtoList = exerciseForClassDtoList;
    }
}
