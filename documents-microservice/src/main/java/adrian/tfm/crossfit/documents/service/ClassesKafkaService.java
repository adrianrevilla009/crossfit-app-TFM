package adrian.tfm.crossfit.documents.service;

import adrian.tfm.crossfit.common.dto.ClassesRequestMessageDto;
import adrian.tfm.crossfit.common.dto.ClassesResponseMessageDto;

public interface ClassesKafkaService {
    /**
     * Given a nif, sends a message through a kafka topic
     * @param topicName
     * @param nif
     */
    void sendGetClassesByNifMessage(String topicName, String nif);

    /**
     * Receives some json data from kafka topic
     * @param classesResponseMessageDto
     * @throws Exception
     */
    void receiveGetClassesByNifMessage(ClassesResponseMessageDto classesResponseMessageDto) throws Exception;
}
