package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.common.dto.ClassesRequestMessageDto;
import adrian.tfm.crossfit.common.dto.ClassesResponseMessageDto;
import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.service.ClassesKafkaService;
import adrian.tfm.crossfit.documents.service.DocumentsService;
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
public class ClassesKafkaServiceImpl implements ClassesKafkaService {
    private final Logger logger = LoggerFactory.getLogger(ClassesKafkaServiceImpl.class);
    private final KafkaTemplate<String, ClassesRequestMessageDto> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private DocumentsService documentsService;

    public ClassesKafkaServiceImpl(KafkaTemplate<String, ClassesRequestMessageDto> kafkaTemplate, ObjectMapper objectMapper,
                                   DocumentsService documentsService) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.documentsService = documentsService;
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

    @Override
    @KafkaListener(topics = "send-classes-topic", groupId = "classes-group")
    public void receiveGetClassesByNifMessage(ClassesResponseMessageDto classesResponseMessageDto) throws Exception {
        logger.info("Task status is updated : " + classesResponseMessageDto.getNif());

        try {
            List<ClassDto> classDtoList = objectMapper.readValue(classesResponseMessageDto.getClassDtoList(), new TypeReference<List<ClassDto>>() {});

            this.documentsService.createFile(classesResponseMessageDto.getNif(), classDtoList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("[ERROR] serializing values on message send on kafka");
        }
    }
}
