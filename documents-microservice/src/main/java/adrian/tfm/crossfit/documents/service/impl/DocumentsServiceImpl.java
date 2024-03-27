package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.model.Document;
import adrian.tfm.crossfit.documents.repository.DocumentRepository;
import adrian.tfm.crossfit.documents.service.DocumentsService;
import adrian.tfm.crossfit.documents.service.ClassesExcelService;
import adrian.tfm.crossfit.documents.service.ClassesKafkaRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DocumentsServiceImpl implements DocumentsService {

    private final Logger logger = LoggerFactory.getLogger(DocumentsServiceImpl.class);

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
            // create excel and save on mongo db
            this.classesExcelService.createExcel(nif, classDtoList);
        } else {
            logger.info("[CREATE EXCEL] Excel not created due to empty data for user");
        }

    }

    public List<Document> findByUser(String nif) {
        return this.documentRepository.findByUser(nif);
    }
}
