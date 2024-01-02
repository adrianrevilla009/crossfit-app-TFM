package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.service.ClassesKafkaResponseService;
import adrian.tfm.crossfit.documents.service.DocumentsService;
import adrian.tfm.library.common.dto.ClassesResponseMessageDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesKafkaResponseServiceImpl implements ClassesKafkaResponseService {
    private final Logger logger = LoggerFactory.getLogger(ClassesKafkaResponseServiceImpl.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private DocumentsService documentsService;

    public ClassesKafkaResponseServiceImpl(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper,
                                          DocumentsService documentsService) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.documentsService = documentsService;
    }

    @Override
    @KafkaListener(topics = "send-classes-topic", groupId = "classes-group")
    public void receiveGetClassesByNifMessage(String jsonMessage) throws Exception {
        logger.info("Task status is updated : " + jsonMessage);

        try {
            ClassesResponseMessageDto classesResponseMessageDto = objectMapper.readValue(jsonMessage, ClassesResponseMessageDto.class);
            List<ClassDto> classDtoList = objectMapper.readValue(classesResponseMessageDto.getClassDtoList(), new TypeReference<List<ClassDto>>() {});

            this.documentsService.createFile(classesResponseMessageDto.getNif(), classDtoList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("[ERROR] serializing values on message send on kafka");
        }
    }

}
