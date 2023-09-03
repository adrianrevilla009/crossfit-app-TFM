package adrian.tfm.crossfit.security.service;

import adrian.tfm.crossfit.security.models.Session;
import adrian.tfm.crossfit.security.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    SessionRepository sessionRepository;

    public void saveData(String email, String token) {
        sessionRepository.save(new Session(email, token));
    }

    public Session getData(String email) {
        return sessionRepository.findById(email).orElse(null);
    }

    public void removeData(String email) {
        sessionRepository.deleteById(email);
    }
}
