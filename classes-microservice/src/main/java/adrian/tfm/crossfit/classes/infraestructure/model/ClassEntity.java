package adrian.tfm.crossfit.classes.infraestructure.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
// @Table(name = "classes" , schema = "classes")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime time;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classEntity")
    private List<UserEntity> userList;

    private Boolean isFull;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "classEntity")
    private List<ExerciseForClassEntity> exerciseForClassEntityList;

    private final int MAX_PEOPLE = 20;

    public Boolean isFullClass() {
        return this.userList.size() > 0 && this.userList.size() >= this.MAX_PEOPLE;
    }

    public ClassEntity(String name, LocalDateTime time, List<UserEntity> userList, Boolean isFull, List<ExerciseForClassEntity> exerciseForClassEntityList) {
        this.name = name;
        this.time = time;
        this.userList = userList;
        this.isFull = isFull;
        this.exerciseForClassEntityList = exerciseForClassEntityList;
    }

    public ClassEntity() {
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

    public List<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public List<ExerciseForClassEntity> getExerciseForClassEntityList() {
        return exerciseForClassEntityList;
    }

    public void setExerciseForClassEntityList(List<ExerciseForClassEntity> exerciseForClassEntityList) {
        this.exerciseForClassEntityList = exerciseForClassEntityList;
    }
}
