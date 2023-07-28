package adrian.tfm.crossfit.classes.domain.port;

import adrian.tfm.crossfit.common.dto.ClassesRequestMessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ClassesKafka {
    void receiveGetClassesByNifMessage(ClassesRequestMessageDto classesRequestMessageDto) throws Exception;

    void sendGetClassesByNifMessage(String topicName, List<ClassDto> classDtoList) throws Exception;
}
