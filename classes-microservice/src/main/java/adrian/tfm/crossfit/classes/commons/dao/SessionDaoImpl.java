package adrian.tfm.crossfit.classes.commons.dao;

import adrian.tfm.crossfit.classes.commons.models.Session;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Primary
public class SessionDaoImpl implements ISessionDao {

    private final String hashReference= "Session";

    @Resource(name="redisTemplate")          // 'redisTemplate' is defined as a Bean in RedisConfig.java
    private HashOperations<String, Integer, Session> hashOperations;

    @Override
    public void saveSession(Session s) {
        //creates one record in Redis DB if record with that Id is not present
        hashOperations.putIfAbsent(hashReference, s.getId(), s);
    }

    @Override
    public void saveAllSession(Map<Integer, Session> map) {
        hashOperations.putAll(hashReference, map);
    }

    @Override
    public Session getOneSession(Integer id) {
        return hashOperations.get(hashReference, id);
    }

    @Override
    public void updateSession(Session s) {
        hashOperations.put(hashReference, s.getId(), s);
    }

    @Override
    public Map<Integer, Session> getAllSession() {
        return hashOperations.entries(hashReference);
    }

    @Override
    public void deleteSession(Integer id) {
        hashOperations.delete(hashReference, id);
    }

    @Override
    public void deleteAllSessions(String hashKey) {
        hashOperations.delete(hashKey, hashOperations.keys(hashKey).toArray());
    }
}
