package adrian.tfm.crossfit.documents.service;

import adrian.tfm.crossfit.documents.model.Document;

import java.util.List;

public interface DocumentsService {
    List<Document> getAllDocuments();

    void createDocument(Document document, String nif);
}
