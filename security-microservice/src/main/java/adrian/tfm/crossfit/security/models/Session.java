package adrian.tfm.crossfit.security.models;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Session")
public class Session implements Serializable {
    @Id
    private String id; // email
    private String token;

    public Session(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public Session() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
