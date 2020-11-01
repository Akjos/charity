package pl.coderslab.charity.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.coderslab.charity.domain.model.Role;
import pl.coderslab.charity.domain.model.User;
import pl.coderslab.charity.domain.repositories.RoleRepository;
import pl.coderslab.charity.domain.repositories.UserRepository;
import pl.coderslab.charity.rest.InvalidDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    private User user;
    private Role userRole;
    private Role adminRole;
    private String userRoleName = "ROLE_USER";
    private String adminRoleName = "ROLE_ADMIN";
    private String userName = "UserName";
    private Long userId = 4L;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private RoleRepository roleRepositoryMock;

    @InjectMocks
    private UserService testObject;

    @Before
    public void setUser() {
        user = User.builder().id(userId).username(userName).role(null).build();
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
        assertTrue(returnUser.isPresent());
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
        assertFalse(argumentCaptor.getValue().getPassword().isEmpty());
        assertEquals(userRole, argumentCaptor.getValue().getRole());
    }

    @Test
    public void shouldEncodePasswordAddUserToDb() {
//        given
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .password("password")
                .build();
        when(roleRepositoryMock.findByName(userRoleName)).thenReturn(userRole);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

//        when
        testObject.addUserToDb(userRegisterDTO);


//        then
        verify(userRepositoryMock).save(argumentCaptor.capture());
        assertTrue(passwordEncoder.matches(userRegisterDTO.getPassword(), argumentCaptor.getValue().getPassword()));
    }

    @Test
    public void shouldReturnAllAdminUserViewDTO() {
//        given
        List<UserViewDTO> list = new ArrayList<>();
        when(userRepositoryMock.findAllActiveUsers(anyString())).thenReturn(list);

//        when
        List<UserViewDTO> returnList = testObject.getAllAdmin();

//        then
        assertNotNull(returnList);
    }

    @Test
    public void shouldUseRoleNameToGetAllAdmin() {
//        given
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        when(userRepositoryMock.findAllActiveUsers(anyString())).thenReturn(anyList());

//        when
        testObject.getAllAdmin();

//        then
        verify(userRepositoryMock).findAllActiveUsers(argumentCaptor.capture());
        assertEquals(argumentCaptor.getValue(), adminRoleName);
    }

    @Test(expected = InvalidDataException.class)
    public void shouldTrowInvalidDataExceptionWhenUserExistInDb() {
//        given
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .username("userName")
                .build();
        when(userRepositoryMock.checkIfUserExistByUsername(anyString())).thenReturn(true);

//        when
        testObject.saveAdmin(userRegisterDTO);

//        then
    }

    @Test
    public void shouldSetDataToSaveAdmin() {
//        given
        UserRegisterDTO userDTO = UserRegisterDTO
                .builder()
                .username(userName)
                .password("password")
                .build();
        user.setRole(adminRole);
        when(roleRepositoryMock.findByName(adminRoleName)).thenReturn(adminRole);
        when(userRepositoryMock.checkIfUserExistByUsername(anyString())).thenReturn(false);
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);


//        when
        testObject.saveAdmin(userDTO);

//        then
        verify(userRepositoryMock).save(argumentCaptor.capture());
        assertEquals(adminRole, argumentCaptor.getValue().getRole());
        assertEquals(argumentCaptor.getValue().getUsername(), userDTO.getUsername());
        assertTrue(argumentCaptor.getValue().isActive());
    }

    @Test
    public void shouldEncodePasswordForAdmin() {
        //        given
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .password("password")
                .build();
        user.setRole(adminRole);
        when(roleRepositoryMock.findByName(adminRoleName)).thenReturn(adminRole);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);

//        when
        testObject.saveAdmin(userRegisterDTO);


//        then
        verify(userRepositoryMock).save(argumentCaptor.capture());
        assertTrue(passwordEncoder.matches(userRegisterDTO.getPassword(), argumentCaptor.getValue().getPassword()));
    }
}