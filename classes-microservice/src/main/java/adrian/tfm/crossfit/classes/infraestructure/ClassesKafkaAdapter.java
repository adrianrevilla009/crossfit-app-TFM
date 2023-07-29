package adrian.tfm.crossfit.classes.infraestructure;

import adrian.tfm.crossfit.classes.config.AppConfig;
import adrian.tfm.crossfit.classes.domain.port.ClassDto;
import adrian.tfm.crossfit.classes.domain.port.ClassesKafka;
import adrian.tfm.crossfit.classes.infraestructure.dao.ClassDaoJpaRepository;
import adrian.tfm.crossfit.classes.infraestructure.mapper.ClassDtoAndEntityMapper;
import adrian.tfm.crossfit.classes.infraestructure.model.ClassEntity;
import adrian.tfm.crossfit.classes.infraestructure.repository.ClassJpaRepository;
import adrian.tfm.crossfit.common.dto.ClassesRequestMessageDto;
import adrian.tfm.crossfit.common.dto.ClassesResponseMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Log
public class ClassesKafkaAdapter implements ClassesKafka {

    private final Logger logger = LoggerFactory.getLogger(ClassesKafkaAdapter.class);

    private final ClassJpaRepository classJpaRepository;
    private final ClassDtoAndEntityMapper classDtoAndEntityMapper;

    private final ClassDaoJpaRepository classDaoJpaRepository;

    private final KafkaTemplate<String, ClassesResponseMessageDto> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public ClassesKafkaAdapter(ClassJpaRepository classJpaRepository, ClassDtoAndEntityMapper classDtoAndEntityMapper,
                               KafkaTemplate<String, ClassesResponseMessageDto> kafkaTemplate,
                               ClassDaoJpaRepository classDaoJpaRepository, ObjectMapper objectMapper) {
        this.classJpaRepository = classJpaRepository;
        this.classDtoAndEntityMapper = classDtoAndEntityMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.classDaoJpaRepository = classDaoJpaRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @KafkaListener(topics = "get-classes-topic", groupId = "classes-group")
    public void receiveGetClassesByNifMessage(ClassesRequestMessageDto classesRequestMessageDto) throws Exception {
        logger.info("Task status is updated : " + classesRequestMessageDto.getNif());

        List<ClassEntity> classEntityList = this.classJpaRepository.findByUserNif(classesRequestMessageDto.getNif());

        this.sendGetClassesByNifMessage("send-classes-topic",
                classEntityList.stream().map(c -> this.classDtoAndEntityMapper.mapFromClassEntityToClassDto(c)).collect(Collectors.toList()),
                classesRequestMessageDto.getNif()
        );
    }

    @Override
    public void sendGetClassesByNifMessage(String topicName, List<ClassDto> classDtoList, String nif) throws Exception {
        String jsonMessage;
        try {
            jsonMessage = objectMapper.writeValueAsString(classDtoList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("[ERROR] serializing values on message send on kafka");
        }

        ClassesResponseMessageDto classesResponseMessageDto = new ClassesResponseMessageDto(jsonMessage, nif);

        var future = kafkaTemplate.send(topicName, classesResponseMessageDto);

        future.whenComplete((sendResult, exception) -> {
            if (exception != null) {
                future.completeExceptionally(exception);
            } else {
                future.complete(sendResult);
            }
            logger.info("Task status send to Kafka topic : "+ topicName);
        });
    }

}
