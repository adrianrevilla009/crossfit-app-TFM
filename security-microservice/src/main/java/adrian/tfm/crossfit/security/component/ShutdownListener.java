package adrian.tfm.crossfit.security.component;

import adrian.tfm.crossfit.security.dao.ISessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {

    private Logger logger = LoggerFactory.getLogger(ShutdownListener.class);

    private final String hashReference= "Session";

    @Autowired
    ISessionDao sessionDao;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        // clean redis cache on server shutdown
        logger.info("Ejecutando método antes de apagar el servidor...");

        sessionDao.deleteAllSessions(hashReference);
    }
}
