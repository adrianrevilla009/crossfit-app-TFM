package adrian.tfm.crossfit.classes.dto.request;

public class UserRequest {
    private String name;

    public UserRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
