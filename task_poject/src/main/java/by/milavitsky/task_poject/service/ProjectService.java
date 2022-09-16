package by.milavitsky.task_poject.service;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.exception.ServiceException;

import java.util.List;

/**
 *  Service layer use methods from repository layer
 */

public interface ProjectService {

    /**
     * Use method findById in repository layer
     * @param id is field project
     * @return entity from database
     * @throws ServiceException if entity by id has not been exist
     */
    ProjectDTO findById(Long id) throws ServiceException;

    /**
     * Use method create in repository layer
     * @param projectDTO entity project
     * @return created entity
     * @throws ServiceException if the entity has not been added to the database
     */
    ProjectDTO create (ProjectDTO projectDTO) throws ServiceException;


    /**
     * Use method update in repository layer
     * @param projectDTO entity project
     * @return updated entity
     * @throws ServiceException if the entity has not been updated to the database
     */
    ProjectDTO update (ProjectDTO projectDTO) throws ServiceException;


    /**
     * Use method delete in repository layer
     * @param id is field entity project
     * @throws ServiceException  if the entity has not been deleted to the database
     */
    void deleteById (Long id) throws ServiceException;

    /**
     * Use method findAll in repository layer
     * @return list of all projects
     */
    List<ProjectDTO> findAll();
}
