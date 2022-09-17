package by.milavitsky.task_poject.repository;

import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.repository.entity.Project;

import java.util.List;

/**
 * Work with project in layer repository
 */
public interface ProjectRepository extends BaseRepository <Project> {

    /**
     * Search project in database by title or description
     * @param part it is String value of word
     * @return list of gift certificates
     * @throws by.milavitsky.task_poject.exception.RepositoryException if certificate have not been found
     */
    List<Project> searchByNameOrDescription(String part) throws RepositoryException;
}
