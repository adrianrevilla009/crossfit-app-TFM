package adrian.tfm.crossfit.classes.infraestructure.mapper;

import adrian.tfm.crossfit.classes.domain.port.UserDto;
import adrian.tfm.crossfit.classes.infraestructure.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoAndEntityMapper {
    public UserDto fromUserEntityToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setName(userEntity.getName());
        userDto.setNif(userEntity.getNif());

        return userDto;
    }

    public UserEntity fromUserDtoToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDto.getId());
        userEntity.setName(userDto.getName());
        userEntity.setNif(userDto.getNif());

        return userEntity;
    }
}
