package adrian.tfm.crossfit.classes.infraestructure.repository;

import adrian.tfm.crossfit.classes.infraestructure.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT * FROM user_entity WHERE nif = ?1", nativeQuery = true)
    UserEntity findByNif(String nif);

    @Modifying
    @Query(value = "DELETE FROM UserEntity u WHERE u.classEntity.id = ?1 AND u.nif = ?2")
    void deleteByClassAndNif(Long id, String nif);
}
