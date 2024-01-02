package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.service.ClassesKafkaRequestService;
import adrian.tfm.crossfit.documents.service.DocumentsService;
import adrian.tfm.library.common.dto.ClassesRequestMessageDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log
public class ClassesKafkaRequestServiceImpl implements ClassesKafkaRequestService {
    private final Logger logger = LoggerFactory.getLogger(ClassesKafkaRequestServiceImpl.class);
    private final KafkaTemplate<String, ClassesRequestMessageDto> kafkaTemplate;

    public ClassesKafkaRequestServiceImpl(KafkaTemplate<String, ClassesRequestMessageDto> kafkaTemplate) {
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
            logger.info("Task status send to Kafka topic : "+ nif);
        });
    }
}
