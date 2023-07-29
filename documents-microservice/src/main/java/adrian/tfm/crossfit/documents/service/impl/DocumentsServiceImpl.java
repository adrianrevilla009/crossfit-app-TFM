package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.model.Document;
import adrian.tfm.crossfit.documents.repository.DocumentRepository;
import adrian.tfm.crossfit.documents.service.DocumentsService;
import adrian.tfm.crossfit.documents.service.ClassesExcelService;
import adrian.tfm.crossfit.documents.service.ClassesKafkaRequestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentsServiceImpl implements DocumentsService {

    private DocumentRepository documentRepository;
    private ClassesExcelService classesExcelService;

    private ClassesKafkaRequestService classesKafkaRequestService;

    public DocumentsServiceImpl(DocumentRepository documentRepository, ClassesExcelService classesExcelService,
                                ClassesKafkaRequestService classesKafkaRequestService) {
        this.documentRepository = documentRepository;
        this.classesExcelService = classesExcelService;
        this.classesKafkaRequestService = classesKafkaRequestService;
    }

    @Override
    public List<Document> getAllDocuments() {
        return this.documentRepository.findAll();
    }

    @Override
    public void createDocument(String nif) {
        this.classesKafkaRequestService.sendGetClassesByNifMessage("get-classes-topic", nif);
    }

    @Override
    public void createFile(String nif, List<ClassDto> classDtoList) throws Exception {
        if (!classDtoList.isEmpty()) {
            Document excelFile = this.classesExcelService.createExcel(nif, classDtoList);
            // this.documentRepository.save(excelFile);
        } else {
            // throw or log
        }

    }
}
