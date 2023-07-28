package adrian.tfm.crossfit.documents.dto;

public class ExerciseForClassDto {
    private Long id;
    private ExerciseDto exercise;
    private int series;
    private int repetitions;

    private String meters;

    public ExerciseForClassDto() {
    }

    public ExerciseForClassDto(ExerciseDto exercise, int series, int repetitions) {
        this.exercise = exercise;
        this.series = series;
        this.repetitions = repetitions;
    }

    public ExerciseForClassDto(ExerciseDto exercise, int series, String meters) {
        this.exercise = exercise;
        this.series = series;
        this.meters = meters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExerciseDto getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDto exercise) {
        this.exercise = exercise;
    }

    public int getSeries() {
        return series;
    }

    public void setSeries(int series) {
        this.series = series;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public String getMeters() {
        return meters;
    }

    public void setMeters(String meters) {
        this.meters = meters;
    }
}
