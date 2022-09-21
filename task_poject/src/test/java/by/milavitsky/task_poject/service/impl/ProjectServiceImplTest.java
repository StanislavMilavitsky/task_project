package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.mapper.Mapper;
import by.milavitsky.task_poject.mapper.ProjectReadMapper;
import by.milavitsky.task_poject.repository.ProjectRepository;
import by.milavitsky.task_poject.entity.Project;
import by.milavitsky.task_poject.entity.Task;
import by.milavitsky.task_poject.repository.TaskRepository;
import by.milavitsky.task_poject.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@SpringBootTest
class ProjectServiceImplTest {

    /**
     * Initialisation test
     */

    @Mock
    ProjectRepository projectRepository;
    ProjectService projectService;
    Project project;
    ProjectDTO projectDTO;
    Mapper mapper;
    List<Task> taskList;
    TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        mapper = new ProjectReadMapper();

        projectService = new ProjectServiceImpl(projectRepository,taskRepository,mapper);

        Task task = new Task(1L, "task description", false);
        Task task2 = new Task(2L, "task 2 description", true);
        taskList = new ArrayList<>();
        taskList.add(task);
        taskList.add(task2);

        project = new Project(1L, "title", "description", new BigDecimal("100"),
                LocalDate.parse("2022-09-15"), LocalDate.parse("2022-10-15"), false, taskList);

        projectDTO = new ProjectDTO(1L, "title", "description", new BigDecimal("100"),
                "2022-09-15", "2022-10-15", false, taskList);
    }

    /**
     * Tests methods
     */

    @Test
    void testFindByIdPositive() throws RepositoryException, ServiceException {
        lenient().when(projectRepository.findById(anyLong())).thenReturn(project);
        ProjectDTO actual = projectService.findById(1L);
        assertEquals(projectDTO, actual);
    }

    @Test
    void testFindByIdNegative() throws RepositoryException, ServiceException {
        lenient().when(projectRepository.findById(anyLong())).thenReturn(project);
        ProjectDTO actual = projectService.findById(2L);
        assertNotEquals(projectDTO, actual);
    }

    @Test
    void testFindException() throws RepositoryException {
        lenient().when(projectRepository.findById(anyLong())).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> projectService.findById(1L));
    }

    @Test
    void testCreatePositive() throws ServiceException, RepositoryException {
        lenient().when(projectRepository.create(any(Project.class))).thenReturn(project);
        ProjectDTO actual = projectService.create(projectDTO);
        assertEquals(projectDTO, actual);
    }

    @Test
    void testCreateNegative() throws ServiceException, RepositoryException {
        lenient().when(projectRepository.create(any(Project.class))).thenReturn(project);
        ProjectDTO actual = projectService.create(projectDTO);
        assertNotEquals(new ProjectDTO(), actual);
    }

    @Test
    void testCreateException() throws RepositoryException {
        lenient().when(projectRepository.create(any(Project.class))).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> projectService.create(projectDTO));
    }

    @Test
    void testUpdatePositive() throws ServiceException, RepositoryException {
        lenient().when(projectRepository.update(any(Project.class))).thenReturn(project);
        lenient().when((projectRepository.findById(anyLong()))).thenReturn(project);
        ProjectDTO actual = projectService.update(projectDTO);
        assertEquals(projectDTO, actual);
    }

    @Test
    void testUpdateNegative() throws ServiceException, RepositoryException {
        lenient().when(projectRepository.update(any(Project.class))).thenReturn(project);
        lenient().when((projectRepository.findById(anyLong()))).thenReturn(project);
        ProjectDTO actual = projectService.update(projectDTO);
        assertNotEquals(new ProjectDTO(), actual);
    }

    @Test
    void testUpdateException() throws RepositoryException {
        lenient().when(projectRepository.update(any(Project.class))).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> projectService.update(projectDTO));
    }

  /*  @Test
    void testDeleteByIdPositive() throws ServiceException, RepositoryException {
        lenient().when(projectRepository.delete(anyLong())).thenAnswer(project.getIsDeleted());
        projectService.deleteById(1L);
        Boolean actual = project.getIsDeleted();
        assertEquals(true, actual);
    }

    @Test
    void testDeleteByIdNegative() throws RepositoryException, ServiceException {
       // lenient().when(projectRepository.delete(anyLong())).thenReturn(1L);
        projectService.deleteById(1L);
        Boolean actual = project.getIsDeleted();
        assertNotEquals(false, actual);
    }*/
}