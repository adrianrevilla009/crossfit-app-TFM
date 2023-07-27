package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.documents.model.Document;
import adrian.tfm.crossfit.documents.repository.DocumentRepository;
import adrian.tfm.crossfit.documents.service.DocumentsService;
import adrian.tfm.crossfit.documents.service.ExcelService;
import adrian.tfm.crossfit.documents.service.KafkaQueueProducerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentsServiceImpl implements DocumentsService {

    private DocumentRepository documentRepository;
    private ExcelService excelService;

    private KafkaQueueProducerService kafkaQueueProducerService;

    public DocumentsServiceImpl(DocumentRepository documentRepository, ExcelService excelService,
                                KafkaQueueProducerService kafkaQueueProducerService) {
        this.documentRepository = documentRepository;
        this.excelService = excelService;
        this.kafkaQueueProducerService = kafkaQueueProducerService;
    }

    @Override
    public List<Document> getAllDocuments() {
        return this.documentRepository.findAll();
    }

    @Override
    public void createDocument(Document document, String nif) {
        this.kafkaQueueProducerService.sendGetClassesByNifMessage("classes-topic", nif);

        String excelFile = this.excelService.createExcel(document);
        document.setFile(excelFile);

        this.documentRepository.save(document);
    }
}
