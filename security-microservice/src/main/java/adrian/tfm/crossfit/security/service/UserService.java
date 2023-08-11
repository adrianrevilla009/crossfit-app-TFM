package adrian.tfm.crossfit.security.service;

import adrian.tfm.crossfit.security.dto.UserCreateDTO;
import adrian.tfm.crossfit.security.dto.UserDTO;
import adrian.tfm.crossfit.security.mapper.UserMapper;
import adrian.tfm.crossfit.security.model.User;
import adrian.tfm.crossfit.security.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO save(UserCreateDTO userDTO) {
        return userMapper.toDTO(userRepository.save(userMapper.toDomain(userDTO)));
    }

    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDTO);
    }

//    public Page<ReviewDTO> findAllByUserId(long id) {
//        List<Review> reviews = userRepository.findById(id).orElseThrow().getReviews();
//        return new PageImpl<>(reviewMapper.toDTOs(reviews));
//    }

    public UserDTO findByIdDTO(long id) {
        return userMapper.toDTO(userRepository.findById(id).orElseThrow());
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public UserDTO replace(UserCreateDTO userDTO, long id) {
        User newUser = userMapper.toDomain(userDTO);
        newUser.setId(id);
        userRepository.findById(newUser.getId()).orElseThrow();
        userRepository.save(newUser);
        return userMapper.toDTO(newUser);
    }

    public UserDTO delete(long id) {
        User user = this.findById(id);
        userRepository.delete(user);
        return userMapper.toDTO(user);
    }
}
