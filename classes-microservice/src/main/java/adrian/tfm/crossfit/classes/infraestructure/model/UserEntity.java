package adrian.tfm.crossfit.classes.infraestructure.model;

import jakarta.persistence.*;

@Entity
// @Table(name = "user" , schema = "classes")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private ClassEntity classEntity;

}
