package adrian.tfm.crossfit.security.data;

import adrian.tfm.crossfit.security.models.ERole;
import adrian.tfm.crossfit.security.models.Role;
import adrian.tfm.crossfit.security.repository.RoleRepository;
import adrian.tfm.crossfit.security.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
        Role role1 = new Role(ERole.ROLE_USER);
        Role role2 = new Role(ERole.ROLE_ADMIN);
        Role role3 = new Role(ERole.ROLE_MODERATOR);

        List<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role2);
        roleList.add(role3);

        this.roleRepository.saveAll(roleList);
    }
}
