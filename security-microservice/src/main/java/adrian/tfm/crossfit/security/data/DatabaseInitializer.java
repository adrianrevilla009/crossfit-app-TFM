package adrian.tfm.crossfit.security.data;

import adrian.tfm.crossfit.security.model.ERole;
import adrian.tfm.crossfit.security.model.Role;
import adrian.tfm.crossfit.security.model.User;
import adrian.tfm.crossfit.security.repository.RoleRepository;
import adrian.tfm.crossfit.security.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseInitializer {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    PasswordEncoder encoder;

    public DatabaseInitializer(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init() {
        Role role1 = new Role(1, ERole.ROLE_USER);
        Role role2 = new Role(2, ERole.ROLE_ADMIN);
        Role role3 = new Role(3, ERole.ROLE_ANONYMOUS_USER);

        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);
        roleList.add(role3);

        this.roleRepository.saveAll(roleList);

        User user1 = new User("Adrian", "adrian@gmail.com");
        user1.setPassword(this.encoder.encode("adrian"));
        Set<Role> user1RoleSet = new HashSet<>();
        user1RoleSet.add(role2);
        user1.setRoles(user1RoleSet);

        userRepository.save(user1);
    }
}
