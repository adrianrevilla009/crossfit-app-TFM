package adrian.tfm.crossfit.security.dao;

import adrian.tfm.crossfit.security.models.Session;

import java.util.Map;

public interface ISessionDao {

    void saveSession(Session s);
    Session getOneSession(Integer id);
    void updateSession(Session s);
    Map<Integer, Session> getAllSession();
    void deleteSession(Integer id);
    void saveAllSession(Map<Integer, Session> map);

    void deleteAllSessions(String hashKey);

}
