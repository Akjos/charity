package pl.coderslab.charity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.domain.model.Role;
import pl.coderslab.charity.domain.model.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SetStartingData implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roles.add(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roles.add(userRole);
            roleRepository.saveAll(roles);
        }

        if (userRepository.findAllActiveUsers("ROLE_ADMIN").isEmpty()) {
            List<User> users = new ArrayList<>();
            User user1 = new User();
            user1.setUsername("admin1");
            user1.setActive(true);
            user1.setPassword(passwordEncoder.encode("pass"));
            user1.setRole(roleRepository.findByName("ROLE_ADMIN"));
            users.add(user1);

            User user2 = new User();
            user2.setUsername("admin2");
            user2.setActive(true);
            user2.setPassword(passwordEncoder.encode("pass"));
            user2.setRole(roleRepository.findByName("ROLE_ADMIN"));
            users.add(user2);

            userRepository.saveAll(users);
        }
    }
}
