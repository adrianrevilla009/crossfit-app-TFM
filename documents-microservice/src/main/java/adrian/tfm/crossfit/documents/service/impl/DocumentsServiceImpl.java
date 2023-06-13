package adrian.tfm.crossfit.documents.service.impl;

import adrian.tfm.crossfit.documents.model.Document;
import adrian.tfm.crossfit.documents.repository.DocumentRepository;
import adrian.tfm.crossfit.documents.service.DocumentsService;
import adrian.tfm.crossfit.documents.service.ExcelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentsServiceImpl implements DocumentsService {

    private DocumentRepository documentRepository;
    private ExcelService excelService;

    public DocumentsServiceImpl(DocumentRepository documentRepository, ExcelService excelService) {
        this.documentRepository = documentRepository;
        this.excelService = excelService;
    }

    @Override
    public List<Document> getAllDocuments() {
        return this.documentRepository.findAll();
    }

    @Override
    public void createDocument(Document document, String nif) {
        // TODO rest call (using a kafka in a future) to get user's classes

        String excelFile = this.excelService.createExcel(document);
        document.setFile(excelFile);

        this.documentRepository.save(document);
    }
}
