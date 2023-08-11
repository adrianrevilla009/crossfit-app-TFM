package adrian.tfm.crossfit.security.repository;

import adrian.tfm.crossfit.security.model.ERole;
import adrian.tfm.crossfit.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
