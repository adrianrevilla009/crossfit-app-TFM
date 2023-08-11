package adrian.tfm.crossfit.documents.service;

import adrian.tfm.crossfit.documents.dto.ClassDto;
import adrian.tfm.crossfit.documents.model.Document;

import java.util.List;

public interface DocumentsService {
    List<Document> getAllDocuments();

    void createDocument(String nif);

    void createFile(String nif, List<ClassDto> classDtoList) throws Exception;

    List<Document> findByUser(String nif);
}
