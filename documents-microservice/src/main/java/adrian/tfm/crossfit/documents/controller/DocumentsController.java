package adrian.tfm.crossfit.documents.controller;

import adrian.tfm.crossfit.documents.model.Document;
import adrian.tfm.crossfit.documents.service.DocumentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentsController {

    private DocumentsService documentsService;

    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @GetMapping("/")
    public List<Document> getAllDocuments() {
        return this.documentsService.getAllDocuments();
    }

    @PostMapping("/user/{nif}")
    public ResponseEntity createDocument(@RequestBody Document document, @PathVariable("nif") String nif) {
        try {
            this.documentsService.createDocument(document, nif);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
