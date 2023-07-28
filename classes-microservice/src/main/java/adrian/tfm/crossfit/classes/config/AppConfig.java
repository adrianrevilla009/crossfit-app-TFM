package adrian.tfm.crossfit.classes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${classes.kafka.topic.get-topic}")
    public static String getTopic;

    public String getTopic() {
        return getTopic;
    }
}
