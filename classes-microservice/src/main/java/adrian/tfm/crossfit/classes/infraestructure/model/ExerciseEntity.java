package adrian.tfm.crossfit.classes.infraestructure.model;

import jakarta.persistence.*;

@Entity
// @Table(name = "exercise" , schema = "classes")
public class ExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
