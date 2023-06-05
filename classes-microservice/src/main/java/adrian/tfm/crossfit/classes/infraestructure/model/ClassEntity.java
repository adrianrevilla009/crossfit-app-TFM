package adrian.tfm.crossfit.classes.infraestructure.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime time;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<UserEntity> userList;

    private Boolean isFull;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<ExerciseForClassEntity> exerciseForClassEntityList;

    private final int MAX_PEOPLE = 20;

    public Boolean isFullClass() {
        return this.userList.size() > 0 && this.userList.size() >= this.MAX_PEOPLE;
    }

}
