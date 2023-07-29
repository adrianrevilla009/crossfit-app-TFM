package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.model.Document;
import adrian.tfm.crossfit.documents.repository.DocumentRepository;
import adrian.tfm.crossfit.documents.service.DocumentsService;
import adrian.tfm.crossfit.documents.service.ExcelService;
import adrian.tfm.crossfit.documents.service.ClassesKafkaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentsServiceImpl implements DocumentsService {

    private DocumentRepository documentRepository;
    private ExcelService excelService;

    private ClassesKafkaService classesKafkaService;

    public DocumentsServiceImpl(DocumentRepository documentRepository, ExcelService excelService,
                                ClassesKafkaService classesKafkaService) {
        this.documentRepository = documentRepository;
        this.excelService = excelService;
        this.classesKafkaService = classesKafkaService;
    }

    @Override
    public List<Document> getAllDocuments() {
        return this.documentRepository.findAll();
    }

    @Override
    public void createDocument(String nif) {
        this.classesKafkaService.sendGetClassesByNifMessage("get-classes-topic", nif);
    }

    @Override
    public void createFile(String nif, List<ClassDto> classDtoList) {
        Document excelFile = this.excelService.createExcel(nif, classDtoList);
        this.documentRepository.save(excelFile);
    }
}
