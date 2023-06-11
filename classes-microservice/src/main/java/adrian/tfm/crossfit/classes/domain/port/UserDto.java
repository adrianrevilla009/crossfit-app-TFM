package adrian.tfm.crossfit.classes.domain.port;

public class UserDto {
    private Long id;
    private String name;

    public UserDto() {
    }

    public UserDto(String name) {
        this.name = name;
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
}
