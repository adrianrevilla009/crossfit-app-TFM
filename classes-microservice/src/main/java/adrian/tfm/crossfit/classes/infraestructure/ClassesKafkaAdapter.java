package adrian.tfm.crossfit.classes.infraestructure;

import adrian.tfm.crossfit.classes.domain.port.ClassesKafka;
import adrian.tfm.crossfit.common.dto.ClassesRequestMessageDto;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log
public class ClassesKafkaAdapter implements ClassesKafka {

    private final Logger logger = LoggerFactory.getLogger(ClassesKafkaAdapter.class);

    @Override
    @KafkaListener(topics = "classes-topic", groupId = "classes-group")
    public void receiveGetClassesByNifMessage(ClassesRequestMessageDto classesRequestMessageDto) {
        logger.info(String.format("Task status is updated : " + classesRequestMessageDto.getNif()));
    }

}
