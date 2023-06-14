package adrian.tfm.crossfit.classes.domain.port;

import java.time.LocalDateTime;

public class ClassExerciseUserDto {
    private Long classId;
    private Boolean isFull;
    private int maxPeople;
    private LocalDateTime time;
    private int repetitions;
    private int series;
    private String exerciseName;
    private String userName;
    private String userNif;

    public ClassExerciseUserDto() {
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNif() {
        return userNif;
    }

    public void setUserNif(String userNif) {
        this.userNif = userNif;
    }
}
