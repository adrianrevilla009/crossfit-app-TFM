package adrian.tfm.crossfit.classes.domain.port;

public interface UserRepository {
    UserDto findByNif(String nif);
}
