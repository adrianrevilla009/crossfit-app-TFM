package adrian.tfm.crossfit.documents.service;

import adrian.tfm.crossfit.common.dto.ClassesResponseMessageDto;

public interface ClassesKafkaRequestService {
    /**
     * Given a nif, sends a message through a kafka topic
     * @param topicName
     * @param nif
     */
    void sendGetClassesByNifMessage(String topicName, String nif);
}
