package adrian.tfm.crossfit.classes.infraestructure;

import adrian.tfm.crossfit.classes.domain.port.UserDto;
import adrian.tfm.crossfit.classes.domain.port.UserRepository;
import adrian.tfm.crossfit.classes.infraestructure.mapper.UserDtoAndEntityMapper;
import adrian.tfm.crossfit.classes.infraestructure.model.UserEntity;
import adrian.tfm.crossfit.classes.infraestructure.repository.UserJpaRepository;

public class UserRepositoryAdapter implements UserRepository {

    private UserJpaRepository userJpaRepository;

    private UserDtoAndEntityMapper userDtoAndEntityMapper;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository, UserDtoAndEntityMapper userDtoAndEntityMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userDtoAndEntityMapper = userDtoAndEntityMapper;
    }

    @Override
    public UserDto findByNif(String nif) {
        // TODO esto tiene que ser una request contra el microservicio de login,
        // TODO de momento no se trae el usuario de bd
        // UserEntity userEntity = this.userJpaRepository.findByNif(nif);
        // assert userEntity != null;
        UserEntity userEntity = new UserEntity("test", nif);

        return this.userDtoAndEntityMapper.fromUserEntityToDto(userEntity);
    }
}
