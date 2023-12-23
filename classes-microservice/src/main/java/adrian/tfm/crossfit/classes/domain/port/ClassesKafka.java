package adrian.tfm.crossfit.classes.domain.port;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface ClassesKafka {
    void receiveGetClassesByNifMessage(adrian.tfm.library.common.dto.ClassesRequestMessageDto classesRequestMessageDto) throws Exception;

    void sendGetClassesByNifMessage(String topicName, List<ClassDto> classDtoList, String nif) throws Exception;
}
