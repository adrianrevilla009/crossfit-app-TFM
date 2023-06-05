package adrian.tfm.crossfit.classes.domain.port;

public class ExerciseForClassDto {
    private Long id;
    private ExerciseDto exercise;
    private int series;
    private int repetitions;

    private String meters;

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
}
