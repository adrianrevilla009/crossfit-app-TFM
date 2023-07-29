package adrian.tfm.crossfit.documents.model;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    @Id
    private String id;
    private String name;
    private String nif;
}
