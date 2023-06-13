package adrian.tfm.crossfit.documents.model;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Document {
    @Id
    private String id;
    private String name;
    private String file;
    private String extension;
    private User user;
}
