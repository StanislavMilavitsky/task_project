package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.entity.Role;
import by.milavitsky.task_poject.entity.User;
import by.milavitsky.task_poject.exception.IncorrectArgumentException;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.repository.UserRepository;
import by.milavitsky.task_poject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@SpringBootTest
class UserServiceImplTest {


    /**
     * Initialisation variables tests
     */

    @Mock
    UserRepository userRepository;
    UserService userService;
    User user;
    User user2;
    @Mock
    PasswordEncoder passwordEncoder;
    List<User> userList;

    @BeforeEach
    void setUp() {

        userService = new UserServiceImpl(userRepository, passwordEncoder);

        user = new User(1L, "username@gmail.com", "password", LocalDate.parse("2022-05-19"), Role.ADMIN, false);
        user2 = new User(2L, "username2@gmail.com", "password2", LocalDate.parse("2022-09-30"), Role.USER, false);

        userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
    }

    @Test
    void testFindByIdPositive() throws RepositoryException, ServiceException {
        lenient().when(userRepository.findById(anyLong())).thenReturn(user);
        User actual = userService.findById(1L);
        assertEquals(user, actual);
    }

    @Test
    void testFindByIdNegative() throws RepositoryException, ServiceException {
        lenient().when(userRepository.findById(anyLong())).thenReturn(user);
        User actual = userService.findById(2L);
        assertNotEquals(new User(), actual);
    }

    @Test
    void testFindByIdException() throws RepositoryException {
        lenient().when(userRepository.findById(anyLong())).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> userService.findById(1L));
    }


    @Test
    void testCreatePositive() throws ServiceException, RepositoryException {
        lenient().when(userRepository.create(any(User.class))).thenReturn(user);
        User actual = userService.create(user);
        assertEquals(user, actual);
    }

    @Test
    void testCreateNegative() throws ServiceException, RepositoryException {
        lenient().when(userRepository.create(any(User.class))).thenReturn(user);
        User actual = userService.create(user);
        assertNotEquals(new User(), actual);
    }

    @Test
    void testCreateException() throws RepositoryException {
        lenient().when(userRepository.create(any(User.class))).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> userService.create(user));
    }

    @Test
    void testUpdatePositive() throws ServiceException, RepositoryException {
        lenient().when(userRepository.update(any(User.class))).thenReturn(user);
        User actual = userService.update(user);
        assertEquals(user, actual);
    }

    @Test
    void testUpdateNegative() throws ServiceException, RepositoryException {
        lenient().when(userRepository.update(any(User.class))).thenReturn(user);
        User actual = userService.update(user);
        assertNotEquals(new User(), actual);
    }

    @Test
    void testUpdateException() throws RepositoryException {
        lenient().when(userRepository.update(any(User.class))).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> userService.update(user));
    }
    @Test
    void testFindAllPositive() throws ServiceException, IncorrectArgumentException {
        lenient().when(userRepository.findAll(anyInt(), anyInt())).thenReturn(userList);
        List<User> actual = userService.findAll(0, 3);
        assertEquals(userList, actual);
    }

    @Test
    void testFindAllNegative() throws ServiceException, IncorrectArgumentException {
        lenient().when(userRepository.findAll(anyInt(), anyInt())).thenReturn(userList);
        List<User> actual = userService.findAll(0, 3);
        assertNotEquals(new ArrayList<User>(), actual);
    }

    @Test
    void testCountPositive() throws ServiceException {
        lenient().when(userRepository.countOfEntity()).thenReturn(2L);
        long actual = userService.count();
        assertEquals(2L, actual);
    }

    @Test
    void testCountNegative() throws ServiceException {
        lenient().when(userRepository.countOfEntity()).thenReturn(2L);
        long actual = userService.count();
        assertNotEquals(1L, actual);
    }

}