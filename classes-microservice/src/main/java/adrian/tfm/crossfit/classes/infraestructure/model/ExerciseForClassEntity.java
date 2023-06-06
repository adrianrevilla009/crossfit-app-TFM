package adrian.tfm.crossfit.classes.infraestructure.model;


import jakarta.persistence.*;

@Entity
// @Table(name = "exercise_for_class" , schema = "classes")
public class ExerciseForClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ExerciseEntity exercise;

    @Column(nullable = false)
    private int series;

    @Column(nullable = false)
    private int repetitions;

    @ManyToOne
    private ClassEntity classEntity;
}
