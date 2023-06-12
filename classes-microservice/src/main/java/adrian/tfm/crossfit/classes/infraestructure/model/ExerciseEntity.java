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

    public ExerciseEntity(String name) {
        this.name = name;
    }

    public ExerciseEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ExerciseEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
