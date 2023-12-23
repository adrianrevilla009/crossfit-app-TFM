package adrian.tfm.crossfit.classes.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    adrian.tfm.library.common.dao.ISessionDao sessionDao;

    public SessionInterceptor(adrian.tfm.library.common.dao.ISessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token;
        try {
            token = adrian.tfm.library.common.security.JwtUtils.parseJwt(request);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        if (token != null) {
            Map<Integer, adrian.tfm.library.common.models.Session> all = this.sessionDao.getAllSession();
            for (Map.Entry<Integer, adrian.tfm.library.common.models.Session> entry : all.entrySet()) {
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
