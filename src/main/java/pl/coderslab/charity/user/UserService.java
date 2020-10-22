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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public void addUserToDb(UserRegisterDTO userDTO) {
        User userToSave = User.builder()
                .active(true)
                .username(userDTO.getUsername())
                .role(roleRepository.findByName("ROLE_USER"))
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        log.debug("Service: User to save in db: {}", userDTO);
        userRepository.save(userToSave);
    }

    public List<UserViewDTO> getAllAdmin() {
        log.debug("Service: Get all user with ROLE_ADMIN");
        return userRepository.findAllActiveUsers("ROLE_ADMIN");
    }

    public UserViewDTO saveAdmin(UserRegisterDTO userRegisterDTO) {
        if (userRepository.countByUsername(userRegisterDTO.getUsername())) {
            throw new InvalidDataException("Duplicate Username");
        }

        User userToSave = User.builder()
                .active(true)
                .username(userRegisterDTO.getUsername())
                .role(roleRepository.findByName("ROLE_ADMIN"))
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .build();

        log.debug("Service: User to save in db: {}", userToSave);
        User saveUser = userRepository.save(userToSave);

        UserViewDTO userViewDTO = UserViewDTO.builder()
                .username(saveUser.getUsername())
                .id(saveUser.getId())
                .role(saveUser.getRole().getName())
                .build();

        return userViewDTO;
    }

    public Optional<User> getById(Long id) {
        log.debug("Service find user by id: {}", id);
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new InvalidDataException();
        }
        log.debug("Institution to delete: {}", user.get());
        userRepository.delete(user.get());
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
        UserViewDTO userViewDTO = UserViewDTO.builder()
                .username(user.getUsername())
                .role(user.getRole().getName())
                .id(user.getId())
                .build();
        return userViewDTO;
    }
}