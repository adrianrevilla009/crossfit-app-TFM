package adrian.tfm.crossfit.classes.config;

import adrian.tfm.crossfit.classes.commons.dao.ISessionDao;
import adrian.tfm.crossfit.classes.commons.models.Session;
import adrian.tfm.crossfit.classes.commons.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Autowired
    @Qualifier("sessionDaoImpl")
    ISessionDao sessionDao;

    public SessionInterceptor(ISessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token;
        try {
            token = JwtUtils.parseJwt(request);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        logger.info(sessionDao.toString());
        logger.info(token);

        if (token != null) {
            Map<Integer, Session> all = this.sessionDao.getAllSession();
            for (Map.Entry<Integer, Session> entry : all.entrySet()) {
                if (entry.getValue().getToken().equals(token)) {
                    return true; // true if continue with request
                } else {
                    throw new Exception("Not matching tokens"); // false if token is another one
                }
            }
        }

        throw new Exception("Token is not present"); // false if token not in redis
    }
}
