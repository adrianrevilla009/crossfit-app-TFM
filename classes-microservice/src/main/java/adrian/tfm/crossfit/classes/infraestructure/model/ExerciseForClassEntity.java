package adrian.tfm.crossfit.classes.infraestructure.model;


import jakarta.persistence.*;

@Entity
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
}
