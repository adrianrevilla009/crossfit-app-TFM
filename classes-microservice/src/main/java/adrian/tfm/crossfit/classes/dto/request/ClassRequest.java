package adrian.tfm.crossfit.classes.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public class ClassRequest {

    private Long id;
    private String name;
    private LocalDateTime time;

    private List<UserRequest> userList;

    private Boolean isFull;
    private List<ExerciseForClassRequest> exerciseForClassDtoList;

    public ClassRequest() {
    }

    public ClassRequest(Long id, String name, LocalDateTime time, List<UserRequest> userList, Boolean isFull, List<ExerciseForClassRequest> exerciseForClassDtoList) {
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

    public List<UserRequest> getUserList() {
        return userList;
    }

    public void setUserList(List<UserRequest> userList) {
        this.userList = userList;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public List<ExerciseForClassRequest> getExerciseForClassDtoList() {
        return exerciseForClassDtoList;
    }

    public void setExerciseForClassDtoList(List<ExerciseForClassRequest> exerciseForClassDtoList) {
        this.exerciseForClassDtoList = exerciseForClassDtoList;
    }
}
