package adrian.tfm.crossfit.security.service;

import adrian.tfm.crossfit.security.controllers.UserRestController;
import adrian.tfm.library.common.dao.ISessionDao;
import adrian.tfm.library.common.models.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    @Qualifier("sessionDaoImpl")
    ISessionDao sessionDao;

    public void saveData(String email, String token) {
        try {
            sessionDao.saveSession(new Session((int)(Math.random()*1000000+1),email, token));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
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
