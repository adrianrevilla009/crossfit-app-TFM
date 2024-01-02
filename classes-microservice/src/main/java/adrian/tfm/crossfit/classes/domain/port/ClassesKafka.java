package adrian.tfm.crossfit.classes.domain.port;

import adrian.tfm.library.common.dto.ClassesRequestMessageDto;

import java.util.List;

public interface ClassesKafka {
    void receiveGetClassesByNifMessage(ClassesRequestMessageDto classesRequestMessageDto) throws Exception;

    void sendGetClassesByNifMessage(String topicName, List<ClassDto> classDtoList, String nif) throws Exception;
}
