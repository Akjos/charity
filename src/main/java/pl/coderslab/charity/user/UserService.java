package pl.coderslab.charity.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.model.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.rest.InvalidDataException;

import java.util.List;

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

    public List<UserViewDTO> getAllAdmin() {
        return userRepository.findAllActiveUsers("ROLE_ADMIN");
    }

    public UserViewDTO saveAdmin(UserRegisterDTO userRegisterDTO) {
        if(userRepository.countByUsername(userRegisterDTO.getUsername())) {
            throw new InvalidDataException("Duplicate Username");
        }
        User adminUser = new User();
        adminUser.setUsername(userRegisterDTO.getUsername());
        User userToSave = new User();
        userToSave.setUsername(userRegisterDTO.getUsername());
        userToSave.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        userToSave.setActive(true);
        userToSave.setRole(roleRepository.findByName("ROLE_ADMIN"));
        User saveUser = userRepository.save(userToSave);
        UserViewDTO userViewDTO = new UserViewDTO();
        userViewDTO.setId(saveUser.getId());
        userViewDTO.setUsername(saveUser.getUsername());
        userViewDTO.setRole(saveUser.getRole().getName());
        return userViewDTO;
    }
}