package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.dto.UserDTO;
import by.milavitsky.task_poject.entity.Project;
import by.milavitsky.task_poject.entity.User;
import by.milavitsky.task_poject.exception.IncorrectArgumentException;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.mapper.Mapper;
import by.milavitsky.task_poject.repository.UserRepository;
import by.milavitsky.task_poject.service.Page;
import by.milavitsky.task_poject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Mapper<UserDTO, User> mapper;

    @Override
    public UserDTO findById(Long id) throws ServiceException {
        try {
            return mapper.toDTO(userRepository.findById(id));
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Cant find user by id=%d !", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public UserDTO create(UserDTO userDTO) throws ServiceException {
        try{
            User user = mapper.fromDTO(userDTO);
            return mapper.toDTO(userRepository.create(user));
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Add user by username=%s exception!", userDTO.getUserName());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public UserDTO update(UserDTO userDTO) throws ServiceException {
        try{
            User user = mapper.fromDTO(userDTO);
            return mapper.toDTO(userRepository.update(user));
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Update user by  username=%s exception!", userDTO.getUserName());
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
    public List<UserDTO> findAll(int page, int size) throws ServiceException, IncorrectArgumentException {
        try {
            long count = count();
            Page userPage = new Page(page, size, count);
            return  userRepository.findAll(userPage.getOffset(), userPage.getLimit()).
                    stream().map(mapper::toDTO).collect(Collectors.toList());
        } catch (DataAccessException exception) {
            String exceptionMessage = "Find all users service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public long count() throws ServiceException {
        try {
            return userRepository.countOfUsers();
        } catch (DataAccessException exception) {
            String exceptionMessage = "Count users service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }
}
