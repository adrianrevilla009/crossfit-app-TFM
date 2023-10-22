package adrian.tfm.crossfit.security.service;

import adrian.tfm.crossfit.common.dao.ISessionDao;
import adrian.tfm.crossfit.common.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    ISessionDao sessionDao;

    public void saveData(String email, String token) {
        sessionDao.saveSession(new Session((int)(Math.random()*1000000+1),email, token));
    }

    public Session getData(String email) {
        Map<Integer, Session> all = this.sessionDao.getAllSession();
        for (Map.Entry<Integer, Session> entry : all.entrySet()) {
            if (entry.getValue().getEmail().equals(email)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void removeData(String email) {
        Map<Integer, Session> all = this.sessionDao.getAllSession();
        for (Map.Entry<Integer, Session> entry : all.entrySet()) {
            if (entry.getValue().getEmail().equals(email)) {
                sessionDao.deleteSession(entry.getKey());
            }
        }
    }
}
