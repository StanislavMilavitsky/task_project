package by.milavitsky.task_poject.service;

import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.repository.entity.Task;

import java.util.List;

/**
 *  Service layer use methods from repository layer
 */
public interface TaskService {
    /**
     * Use method findById in repository layer
     * @param id is field task
     * @return entity from database
     * @throws ServiceException if entity by id has not been exist
     */
    Task findById(Long id) throws ServiceException;

    /**
     * Use method create in repository layer
     * @param task entity
     * @return created entity
     * @throws ServiceException if the entity has not been added to the database
     */
    Task create (Task task) throws ServiceException;


    /**
     * Use method update in repository layer
     * @param task entity
     * @return updated entity
     * @throws ServiceException if the entity has not been updated to the database
     */
    Task update (Task task) throws ServiceException;


    /**
     * Use method delete in repository layer
     * @param id is field entity task
     * @throws ServiceException  if the entity has not been deleted to the database
     */

    void deleteById (Long id) throws ServiceException;

    /**
     * Use method findAll in repository layer
     * @return list of all tasks
     */
    List<Task> findAll();
}
