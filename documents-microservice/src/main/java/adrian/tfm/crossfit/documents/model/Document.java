package adrian.tfm.crossfit.documents.model;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Document {
    @Id
    private String id;
    private String name;
    private String file;
    private String extension;
    private User user;
}
