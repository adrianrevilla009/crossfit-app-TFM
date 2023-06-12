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

    @Column(nullable = false)
    private String nif;

    @ManyToOne
    private ClassEntity classEntity;

    public UserEntity(String name, String nif, ClassEntity classEntity) {
        this.name = name;
        this.nif = nif;
        this.classEntity = classEntity;
    }

    public UserEntity(String name, String nif) {
        this.name = name;
        this.nif = nif;
    }

    public UserEntity() {
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

    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
