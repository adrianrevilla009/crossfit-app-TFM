package adrian.tfm.crossfit.documents.dto;

public class UserDto {
    private Long id;
    private String name;

    private String nif;

    public UserDto() {
    }

    public UserDto(String name, String nif) {
        this.name = name;
        this.nif = nif;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }
}
