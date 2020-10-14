package pl.coderslab.charity.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.model.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public void addUserToDb(UserRegisterDTO userDTO) {
        User userToSave = new User();
        userToSave.setUsername(userDTO.getUsername());
        userToSave.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userToSave.setActive(true);
        userToSave.setRole(roleRepository.findByName("ROLE_USER"));
        userRepository.save(userToSave);
    }
}
