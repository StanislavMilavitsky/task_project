package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.repository.TaskRepository;
import by.milavitsky.task_poject.entity.Task;
import by.milavitsky.task_poject.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task findById(Long id) throws ServiceException {
        try {
            return taskRepository.findById(id);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Cant find task by id=%d !", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public Task create(Task task) throws ServiceException {
        try{
            return taskRepository.create(task);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Add task by description= %s exception!", task.getTaskDescription());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public Task update(Task task) throws ServiceException {
        try {
            return taskRepository.update(task);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Update task by description= %s exception!", task.getTaskDescription());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        try{
            taskRepository.delete(id);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Delete task by id=%d exception!", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }


    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }
}
