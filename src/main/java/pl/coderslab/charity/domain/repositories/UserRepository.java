package pl.coderslab.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.charity.domain.model.User;
import pl.coderslab.charity.user.UserViewDTO;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT new pl.coderslab.charity.user.UserViewDTO(u.id, u.username, u.role.name) FROM User u WHERE u.active = TRUE AND u.role.name = :roleName")
    List<UserViewDTO> findAllActiveUsers(String roleName);

    @Query(value = "SELECT CASE WHEN (COUNT(u) > 0) THEN TRUE ELSE FALSE END FROM User u WHERE u.username = :username")
    Boolean checkIfUserExistByUsername(String username);
}
