package by.milavitsky.task_poject.repository.impl;

import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.repository.ProjectRepository;
import by.milavitsky.task_poject.repository.entity.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository {

    @Override
    public Project create(Project entity) throws RepositoryException {
        return null;
    }

    @Override
    public Project findById(Long id) throws RepositoryException {
        return null;
    }

    @Override
    public Project update(Project entity) throws RepositoryException {
        return null;
    }

    @Override
    public void delete(Long id) throws RepositoryException {

    }

    @Override
    public List<Project> findAll() {
        return null;
    }
}
