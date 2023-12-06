package adrian.tfm.crossfit.documents.controller;

import adrian.tfm.crossfit.documents.model.Document;
import adrian.tfm.crossfit.documents.service.DocumentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentsController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentsController.class);
    private DocumentsService documentsService;

    public DocumentsController(DocumentsService documentsService) {
        this.documentsService = documentsService;
    }

    @GetMapping("/")
    public List<Document> getAllDocuments() {
        return this.documentsService.getAllDocuments();
    }

    @PostMapping("/user/{nif}")
    public ResponseEntity createDocument(@PathVariable("nif") String nif) {
        logger.info("### createDocument ###");
        try {
            this.documentsService.createDocument(nif);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/{nif}")
    public ResponseEntity findByUser(@PathVariable("nif") String nif) {
        logger.info("### findByUser ###");
        try {
            List<Document> documents = this.documentsService.findByUser(nif);
            return ResponseEntity.ok().body(documents);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
