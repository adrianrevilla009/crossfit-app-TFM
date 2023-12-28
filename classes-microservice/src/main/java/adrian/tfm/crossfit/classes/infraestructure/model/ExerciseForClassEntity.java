package adrian.tfm.crossfit.classes.infraestructure.model;


import jakarta.persistence.*;

@Entity
public class ExerciseForClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "exercise_class_id")
    private Long id;

    @ManyToOne
    private ExerciseEntity exercise;

    @Column(nullable = false)
    private int series;

    @Column(nullable = false)
    private int repetitions;

    @ManyToOne
    private ClassEntity classEntity;

    public ExerciseForClassEntity(ExerciseEntity exercise, int series, int repetitions, ClassEntity classEntity) {
        this.exercise = exercise;
        this.series = series;
        this.repetitions = repetitions;
        this.classEntity = classEntity;
    }

    public ExerciseForClassEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExerciseEntity getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseEntity exercise) {
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

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }
}
