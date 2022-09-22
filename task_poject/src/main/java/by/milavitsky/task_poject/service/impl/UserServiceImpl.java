package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.entity.User;
import by.milavitsky.task_poject.exception.IncorrectArgumentException;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.exception.ServiceException;

import by.milavitsky.task_poject.repository.UserRepository;
import by.milavitsky.task_poject.service.Page;
import by.milavitsky.task_poject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) throws ServiceException {
        try {
            return userRepository.findById(id);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Cant find user by id=%d !", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public User create(User user) throws ServiceException {
        try{
                return userRepository.create(user);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Add user by username=%s exception!", user.getUserName());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public User update(User user) throws ServiceException {
        try{
            return userRepository.update(user);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Update user by  username=%s exception!", user.getUserName());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        try{
            userRepository.delete(id);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Delete user by id=%d exception!", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
   // @PostFilter("filterObject.role.name().equals('ADMIN')")
    public List<User> findAll(int page, int size) throws ServiceException, IncorrectArgumentException {
        try {
            long count = count();
            Page userPage = new Page(page, size, count);
            return new ArrayList<>(userRepository.findAll(userPage.getOffset(), userPage.getLimit()));
        } catch (DataAccessException exception) {
            String exceptionMessage = "Find all users service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public long count() throws ServiceException {
        try {
            return userRepository.countOfEntity();
        } catch (DataAccessException exception) {
            String exceptionMessage = "Count users service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUserName(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user:" + username));
    }
}
