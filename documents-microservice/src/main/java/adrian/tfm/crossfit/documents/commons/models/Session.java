package adrian.tfm.crossfit.documents.commons.models;

import java.io.Serializable;

public class Session implements Serializable {

    private static final long serialVersionUID = -7817224776021728682L;

    private Integer id;

    private String email;
    private String token;

    public Session(Integer id, String email, String token) {
        this.id = id;
        this.email = email;
        this.token = token;
    }

    public Session() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
