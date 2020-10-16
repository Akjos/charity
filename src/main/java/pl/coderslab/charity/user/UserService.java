package pl.coderslab.charity.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.domain.model.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.rest.InvalidDataException;

import java.util.List;
import java.util.Optional;

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
        if (userRepository.countByUsername(userRegisterDTO.getUsername())) {
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

    public ResponseEntity getById(Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public User deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new InvalidDataException();
        }
        log.debug("Institution to delete: {}", user.get());
        userRepository.delete(user.get());
        return user.get();
    }

    public UserViewDTO update(Long id, UserRegisterDTO userRegisterDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new InvalidDataException("User with id:" + id + " dont exist");
        }
        User user = userOptional.get();
        if (!user.getUsername().equals(userRegisterDTO.getUsername())) {
            if (userRepository.countByUsername(userRegisterDTO.getUsername())) {
                throw new InvalidDataException("Duplicate Username");
            }
            user.setUsername(userRegisterDTO.getUsername());
        }
        if (!userRegisterDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        }
        userRepository.save(user);
        UserViewDTO userViewDTO = new UserViewDTO();
        userViewDTO.setId(user.getId());
        userViewDTO.setUsername(user.getUsername());
        userViewDTO.setRole(user.getRole().getName());
        return userViewDTO;
    }
}