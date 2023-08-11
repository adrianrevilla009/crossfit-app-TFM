package adrian.tfm.crossfit.security.mapper;

import adrian.tfm.crossfit.security.dto.UserCreateDTO;
import adrian.tfm.crossfit.security.dto.UserDTO;
import adrian.tfm.crossfit.security.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toDomain(UserDTO userDTO);

    User toDomain(UserCreateDTO userDTO);
}
