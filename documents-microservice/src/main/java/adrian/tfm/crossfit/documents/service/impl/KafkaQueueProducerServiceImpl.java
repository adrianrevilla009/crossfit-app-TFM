package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.common.dto.ClassesRequestMessageDto;
import adrian.tfm.crossfit.documents.service.KafkaQueueProducerService;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log
public class KafkaQueueProducerServiceImpl implements KafkaQueueProducerService {
    private final Logger LOGGER = LoggerFactory.getLogger(KafkaQueueProducerServiceImpl.class);
    private final KafkaTemplate<String, ClassesRequestMessageDto> kafkaTemplate;
    private final String topic = "classes-topic";

    public KafkaQueueProducerServiceImpl(KafkaTemplate<String, ClassesRequestMessageDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendGetClassesByNifMessage(String topicName, String nif) {
        var future = kafkaTemplate.send(topicName, new ClassesRequestMessageDto(nif));

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
            LOGGER.info("Task status send to Kafka topic : "+ nif);
        });
    }
}
