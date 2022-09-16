package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.mapper.Mapper;
import by.milavitsky.task_poject.repository.ProjectRepository;
import by.milavitsky.task_poject.repository.entity.Project;
import by.milavitsky.task_poject.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final Mapper<ProjectDTO, Project> mapper;

    @Override
    public ProjectDTO findById(Long id) throws ServiceException {
        try {
            return  mapper.toDto(projectRepository.findById(id));
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Cant find project by id=%d !", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public ProjectDTO create(ProjectDTO projectDTO) throws ServiceException {
        try{
            Project project = mapper.fromDto(projectDTO);
            return mapper.toDto(projectRepository.create(project));
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Add project by title= %s exception!", projectDTO.getTitle());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public ProjectDTO update(ProjectDTO projectDTO) throws ServiceException {
        try{
            Project project = projectRepository.update(mapper.fromDto(projectDTO));
            return mapper.toDto(project);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Update project by title= %s exception!", projectDTO.getTitle());
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        try{
            projectRepository.delete(id);
        } catch (RepositoryException exception) {
            String exceptionMessage = String.format("Delete project by id=%d exception!", id);
            log.error(exceptionMessage, exception);
            throw new ServiceException(exceptionMessage, exception);
        }
    }

    @Override
    public List<ProjectDTO> findAll() {
        List<Project> employeeDao = projectRepository.findAll();
        return employeeDao.stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
