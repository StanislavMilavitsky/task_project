package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    @Override
    public ProjectDTO findById(Long id) throws ServiceException {
        return null;
    }

    @Override
    public ProjectDTO create(ProjectDTO projectDTO) throws ServiceException {
        return null;
    }

    @Override
    public ProjectDTO update(ProjectDTO projectDTO) throws ServiceException {
        return null;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {

    }

    @Override
    public List<ProjectDTO> findAll() {
        return null;
    }
}
