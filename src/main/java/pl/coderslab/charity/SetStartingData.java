package pl.coderslab.charity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.domain.model.Role;
import pl.coderslab.charity.domain.repositories.RoleRepository;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SetStartingData implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Role> roles = roleRepository.findAll();
        if(roles.isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roles.add(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roles.add(userRole);
            roleRepository.saveAll(roles);
        }
    }
}
