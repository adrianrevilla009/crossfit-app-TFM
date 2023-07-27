package adrian.tfm.crossfit.classes.domain.port;

import adrian.tfm.crossfit.common.dto.ClassesRequestMessageDto;

public interface ClassesKafka {
    void receiveGetClassesByNifMessage(ClassesRequestMessageDto classesRequestMessageDto);
}
