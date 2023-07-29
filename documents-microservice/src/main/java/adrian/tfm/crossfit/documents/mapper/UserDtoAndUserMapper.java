package adrian.tfm.crossfit.documents.mapper;

import adrian.tfm.crossfit.documents.dto.UserDto;
import adrian.tfm.crossfit.documents.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoAndUserMapper {
    public User userDtoToUserMap(UserDto userDto) {
        return User.builder()
                .id(userDto.getId().toString())
                .name(userDto.getName())
                .nif(userDto.getNif())
                .build();
    }
}
