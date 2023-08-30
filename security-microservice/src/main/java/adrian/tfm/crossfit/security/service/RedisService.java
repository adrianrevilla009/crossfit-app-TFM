package adrian.tfm.crossfit.security.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveData(String key, Object value) {
        String finalKey = "user:" + key;
        redisTemplate.opsForValue().set(finalKey, value);
    }

    public Object getData(String key) {
        String finalKey = "user:" + key;
        return redisTemplate.opsForValue().get(finalKey);
    }

    public void removeData(String key) {
        Object obj = this.getData(key);
        if (obj != null) {
            String finalKey = "user:" + key;
            redisTemplate.delete(finalKey);
        }
    }
}
