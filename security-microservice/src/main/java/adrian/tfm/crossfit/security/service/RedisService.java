package adrian.tfm.crossfit.security.service;


import adrian.tfm.crossfit.security.commons.models.Session;

public interface RedisService {
    void saveData(String email, String token);

    Session getData(String email);

    void removeData(String email);
}
