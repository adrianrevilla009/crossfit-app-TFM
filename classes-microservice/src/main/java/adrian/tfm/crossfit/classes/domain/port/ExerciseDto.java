package adrian.tfm.crossfit.classes.domain.port;

public class ExerciseDto {
    private Long id;
    private String name;

    public ExerciseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
