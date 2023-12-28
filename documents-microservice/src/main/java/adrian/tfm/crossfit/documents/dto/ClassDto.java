package adrian.tfm.crossfit.documents.dto;

import adrian.tfm.crossfit.documents.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class ClassDto {
    private Long id;
    private String name;
    private LocalDateTime time;

    private List<UserDto> userList;

    private Boolean isFull;
    private List<ExerciseForClassDto> exerciseForClassDtoList;

    public ClassDto() {
    }

    public ClassDto(String name, LocalDateTime time, List<UserDto> userList, Boolean isFull, List<ExerciseForClassDto> exerciseForClassDtoList) {
        this.name = name;
        this.time = time;
        this.userList = userList;
        this.isFull = isFull;
        this.exerciseForClassDtoList = exerciseForClassDtoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<UserDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDto> userList) {
        this.userList = userList;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public List<ExerciseForClassDto> getExerciseForClassDtoList() {
        return exerciseForClassDtoList;
    }

    public void setExerciseForClassDtoList(List<ExerciseForClassDto> exerciseForClassDtoList) {
        this.exerciseForClassDtoList = exerciseForClassDtoList;
    }

    public static String getUserStringList(List<UserDto> userList) {
        String result = "";
        for (UserDto userDto : userList) {
            result = result + "ID: " + userDto.getId() + ", Name: " + userDto.getName() + ", NIF: " + userDto.getNif() + " \n";
        }
        return result;
    }

    public static String getExerciseStringList(List<ExerciseForClassDto> exerciseList) {
        String result = "";
        for (ExerciseForClassDto exercise : exerciseList) {
            result = result + "Name: " + exercise.getExercise().getName() + ", Series: " + exercise.getSeries() + ", Repetitions: " + exercise.getRepetitions() + " \n";
        }
        return result;
    }
}
