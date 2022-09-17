package by.milavitsky.task_poject.repository;

import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.repository.entity.Project;
import javafx.scene.control.TableColumn;

import java.util.List;

/**
 * Work with project in layer repository
 */
public interface ProjectRepository extends BaseRepository <Project> {

    /**
     * Search projects in database by title or description
     * @param part it is String value of word
     * @return list of projects
     * @throws RepositoryException if projects have not been found
     */
    List<Project> searchByTitleOrDescription(String part) throws RepositoryException;

    /**
     * Select entity from database and sort them by title
     * @param sortType is type from enum class TableSortType
     * @return list of sorted entity
     * @throws RepositoryException if projects have not been sorted
     */
    List<Project> sortByTitle(TableColumn.SortType sortType) throws RepositoryException;
}
