package adrian.tfm.crossfit.classes.dto.response;

public class ExerciseForClassResponse {
    private ExerciseResponse exercise;
    private int series;
    private int repetitions;
    private String meters;

    public ExerciseForClassResponse(ExerciseResponse exercise, int series, int repetitions, String meters) {
        this.exercise = exercise;
        this.series = series;
        this.repetitions = repetitions;
        this.meters = meters;
    }

    public ExerciseResponse getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseResponse exercise) {
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
