package adrian.tfm.crossfit.classes.dto.request;

public class UserRequest {
    private String name;

    private String nif;

    public UserRequest() {
    }

    public UserRequest(String name, String nif) {
        this.name = name;
        this.nif  = nif;
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
