package by.milavitsky.task_poject.service;

import by.milavitsky.task_poject.dto.UserDTO;
import by.milavitsky.task_poject.exception.IncorrectArgumentException;
import by.milavitsky.task_poject.exception.ServiceException;

import java.util.List;

public interface UserService extends BaseService<UserDTO> {

    List<UserDTO> findAll(int page, int size) throws ServiceException, IncorrectArgumentException;

    long count() throws ServiceException;
}
