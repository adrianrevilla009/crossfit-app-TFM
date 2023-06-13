package adrian.tfm.crossfit.documents.model;

import jakarta.persistence.Id;

public class User {
    @Id
    private String id;
    private String name;
    private String nif;
}
