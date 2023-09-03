package adrian.tfm.crossfit.security.repository;

import adrian.tfm.crossfit.security.models.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {}
