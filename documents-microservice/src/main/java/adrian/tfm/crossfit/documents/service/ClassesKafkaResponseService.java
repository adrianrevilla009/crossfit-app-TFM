package adrian.tfm.crossfit.documents.service;

import adrian.tfm.crossfit.common.dto.ClassesResponseMessageDto;

public interface ClassesKafkaResponseService {
    /**
     * Receives some json data mapped on an object from kafka topic
     * @param classesResponseMessageDto
     * @throws Exception
     */
    void receiveGetClassesByNifMessage(ClassesResponseMessageDto classesResponseMessageDto) throws Exception;
}
