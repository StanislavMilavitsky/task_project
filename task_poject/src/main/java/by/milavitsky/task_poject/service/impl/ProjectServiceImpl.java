package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.entity.Task;
import by.milavitsky.task_poject.exception.IncorrectArgumentException;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.mapper.Mapper;
import by.milavitsky.task_poject.repository.ProjectRepository;
import by.milavitsky.task_poject.entity.Project;
import by.milavitsky.task_poject.repository.TaskRepository;
import by.milavitsky.task_poject.service.Page;
import by.milavitsky.task_poject.service.ProjectService;
import by.milavitsky.task_poject.entity.SortType;
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
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final Mapper<ProjectDTO, Project> mapper;

    @Override
    public ProjectDTO findById(Long id) throws ServiceException {
        try {
            List<Task> tasks = taskRepository.findAllTaskByProjectId(id);
            ProjectDTO projectDTO = mapper.toDTO(projectRepository.findById(id));
            projectDTO.setTasks(tasks);
            return projectDTO;
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Cant find project by id=%d !", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public ProjectDTO create(ProjectDTO projectDTO) throws ServiceException {
        try {
            Project project = mapper.fromDTO(projectDTO);
            return mapper.toDTO(projectRepository.create(project));
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Add project by title= %s exception!", projectDTO.getTitle());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public ProjectDTO update(ProjectDTO projectDTO) throws ServiceException {
        try {
            Project project = projectRepository.update(mapper.fromDTO(projectDTO));
            return mapper.toDTO(project);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Update project by title= %s exception!", projectDTO.getTitle());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        try {
            projectRepository.delete(id);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Delete project by id=%d exception!", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<ProjectDTO> findAll(int page, int size) throws ServiceException, IncorrectArgumentException {
        try {
            long count = count();
            Page userPage = new Page(page, size, count);
            List<Project> tags = projectRepository.findAll(userPage.getOffset(), userPage.getLimit());
            for (Project project : tags) {
                List<Task> tasks = taskRepository.findAllTaskByProjectId(project.getId());
                project.setTasks(tasks);
            }
            return tags.stream().map(mapper::toDTO).collect(Collectors.toList());
        } catch (DataAccessException exception) {
            String exceptionMessage = "Find all users service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }

    }

    @Override
    public List<ProjectDTO> searchByTitleOrDescription(String part) throws ServiceException {
        try {
            List<Project> projects = projectRepository.searchByTitleOrDescription(part);
            for (Project project : projects) {
                List<Task> tasks = taskRepository.findAllTaskByProjectId(project.getId());
                project.setTasks(tasks);
            }
            return projects.stream().map(mapper::toDTO).collect(Collectors.toList());
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Find project by word=%s exception!", part);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<ProjectDTO> sortByTitle(SortType sortType) throws ServiceException {
        try {
            List<Project> projects = projectRepository.sortByTitle(sortType);
            return projects.stream().map(mapper::toDTO).collect(Collectors.toList());
        } catch (RepositoryException exception) {
            String exceptionMessage = "Sort projects by title";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<ProjectDTO> sortByDateStart(SortType sortType) throws ServiceException {
        try {
            List<Project> projects = projectRepository.sortByDateOfStart(sortType);
            return projects.stream().map(mapper::toDTO).collect(Collectors.toList());
        } catch (RepositoryException exception) {
            String exceptionMessage = "Sort projects by date of start";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<ProjectDTO> sortByDateEnd(SortType sortType) throws ServiceException {
        try {
            List<Project> projects = projectRepository.sortByDateOfEnd(sortType);
            return projects.stream().map(mapper::toDTO).collect(Collectors.toList());
        } catch (RepositoryException exception) {
            String exceptionMessage = "Sort projects by date of start";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public long count() throws ServiceException {
        try {
            return projectRepository.countOfEntity();
        } catch (DataAccessException exception) {
            String exceptionMessage = "Count projects service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }


    @Override
    public List<ProjectDTO> findAllByUser(int page, int size) throws ServiceException, IncorrectArgumentException {
        try {
            long count = countNotDeleted();
            Page userPage = new Page(page, size, count);
            List<Project> tags = projectRepository.findAllNotDeleted(userPage.getOffset(), userPage.getLimit());
            for (Project project : tags) {
                List<Task> tasks = taskRepository.findAllTaskByProjectIdNotDeleted(project.getId());
                project.setTasks(tasks);
            }
            return tags.stream().map(mapper::toDTO).collect(Collectors.toList());
        } catch (DataAccessException exception) {
            String exceptionMessage = "Find all users service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public long countNotDeleted() throws ServiceException {
        try {
            return projectRepository.countOfProjectsNotDeleted();
        } catch (DataAccessException exception) {
            String exceptionMessage = "Count projects service exception!";
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }
}
