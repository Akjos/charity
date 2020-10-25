package pl.coderslab.charity.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.coderslab.charity.domain.model.Role;
import pl.coderslab.charity.domain.model.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private User user;
    private Role userRole;
    private Role adminRole;
    private String userRoleName = "ROLE_USER";
    private String adminRoleName = "ROLE_ADMIN";
    private Long userId = 4L;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private RoleRepository roleRepositoryMock;

    @InjectMocks
    private UserService testObject;

    @Before
    public void setUser() {
        user = User.builder().id(userId).build();
        userRole = Role.builder().id(1L).name(userRoleName).build();
        adminRole = Role.builder().id(2L).name(adminRoleName).build();
    }

    @Test
    public void shouldGetById() {
//        given
        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(user));

//        when
        Optional<User> returnUser = testObject.getById(userId);

//        then
        assertEquals(user, returnUser.get());

    }

    @Test
    public void shouldSetDataFromUserDTOAddUserToDb() {
//        given
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        UserRegisterDTO userRegisterDTO = UserRegisterDTO
                .builder()
                .username("userName")
                .password("userPassword")
                .build();
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);
        when(roleRepositoryMock.findByName(userRoleName)).thenReturn(userRole);

//        when
        testObject.addUserToDb(userRegisterDTO);

//        then
        verify(userRepositoryMock, times(1)).save(argumentCaptor.capture());
        assertEquals(userRegisterDTO.getUsername(), argumentCaptor.getValue().getUsername());
        assertEquals(passwordEncoder.encode(userRegisterDTO.getPassword()), argumentCaptor.getValue().getPassword());
        assertEquals(userRole, argumentCaptor.getValue().getRole());

    }

    @Test
    public void shouldEncodePasswordAddUserToDb() {
//        given
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

    }

}