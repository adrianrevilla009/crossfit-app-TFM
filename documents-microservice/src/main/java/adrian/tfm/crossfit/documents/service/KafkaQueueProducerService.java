package adrian.tfm.crossfit.documents.service;

public interface KafkaQueueProducerService {
    /**
     * Given a nif, sends a message through a kafka topic
     * @param topicName
     * @param nif
     */
    void sendGetClassesByNifMessage(String topicName, String nif);
}
