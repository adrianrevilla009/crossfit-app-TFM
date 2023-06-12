package adrian.tfm.crossfit.classes.dto.request;


public class ExerciseForClassRequest {
    private ExerciseRequest exercise;
    private int series;
    private int repetitions;
    private String meters;

    public ExerciseForClassRequest(ExerciseRequest exercise, int series, int repetitions, String meters) {
        this.exercise = exercise;
        this.series = series;
        this.repetitions = repetitions;
        this.meters = meters;
    }

    public ExerciseRequest getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseRequest exercise) {
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
