package by.milavitsky.task_poject.service;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.exception.ServiceException;
import javafx.scene.control.TableColumn;

import java.util.List;

/**
 *  Service layer use methods from repository layer
 */

public interface ProjectService extends BaseService<ProjectDTO> {

    /**
     * Use method repository layer that find project by title or description
     * @param part of word that must be searched
     * @return list of found projects
     * @throws ServiceException if the projects has not been
     */
    List<ProjectDTO> searchByTitleOrDescription(String part) throws ServiceException;

    /**
     * Use method of repository layer and sort by title
     * @param sortType enum value of Class TableColumn.SortType
     * @return sorted list
     * @throws ServiceException if the projects has not been
     */
    List<ProjectDTO> sortByTitle(TableColumn.SortType sortType) throws ServiceException;

    /**
     * Use method of repository layer and sort by date start
     * @param sortType enum value of Class TableColumn.SortType
     * @return sorted list
     * @throws ServiceException if the projects has not been
     */
    List<ProjectDTO> sortByDateStart(TableColumn.SortType sortType) throws ServiceException;

    /**
     * Use method of repository layer and sort by date end
     * @param sortType enum value of Class TableColumn.SortType
     * @return sorted list
     * @throws ServiceException if the projects has not been
     */
    List<ProjectDTO> sortByDateEnd(TableColumn.SortType sortType) throws ServiceException;
}
