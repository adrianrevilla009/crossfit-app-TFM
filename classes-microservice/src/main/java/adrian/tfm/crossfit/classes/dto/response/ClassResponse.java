package adrian.tfm.crossfit.classes.dto.response;

import adrian.tfm.crossfit.classes.dto.request.ExerciseForClassRequest;
import adrian.tfm.crossfit.classes.dto.request.UserRequest;

import java.time.LocalDateTime;
import java.util.List;

public class ClassResponse {

    private Long id;
    private String name;
    private LocalDateTime time;

    private List<UserResponse> userList;

    private Boolean isFull;
    private List<ExerciseForClassResponse> exerciseForClassDtoList;

    public ClassResponse() {
    }

    public ClassResponse(Long id, String name, LocalDateTime time, List<UserResponse> userList, Boolean isFull, List<ExerciseForClassResponse> exerciseForClassDtoList) {
        this.id = id;
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

    public List<UserResponse> getUserList() {
        return userList;
    }

    public void setUserList(List<UserResponse> userList) {
        this.userList = userList;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public List<ExerciseForClassResponse> getExerciseForClassDtoList() {
        return exerciseForClassDtoList;
    }

    public void setExerciseForClassDtoList(List<ExerciseForClassResponse> exerciseForClassDtoList) {
        this.exerciseForClassDtoList = exerciseForClassDtoList;
    }
}
