package adrian.tfm.crossfit.security.service;

import adrian.tfm.crossfit.security.dto.UserCreateDTO;
import adrian.tfm.crossfit.security.dto.UserDTO;
import adrian.tfm.crossfit.security.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserDTO save(UserCreateDTO userDTO);

    Page<UserDTO> findAll(Pageable pageable);

    UserDTO findByIdDTO(long id);

    User findById(long id);

    UserDTO replace(UserCreateDTO userDTO, long id);

    UserDTO delete(long id);
}
