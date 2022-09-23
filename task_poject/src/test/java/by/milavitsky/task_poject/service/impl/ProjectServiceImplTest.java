package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.entity.SortType;
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

import static by.milavitsky.task_poject.entity.SortType.ASC;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;

@SpringBootTest
class ProjectServiceImplTest {

    /**
     * Initialisation variables tests
     */

    @Mock
    ProjectRepository projectRepository;
    ProjectService projectService;
    Project project;
    ProjectDTO projectDTO;
    Mapper mapper;
    List<Task> taskList;
    @Mock
    TaskRepository taskRepository;
    Task task ;
    Task task2;

    @BeforeEach
    void setUp() {
        mapper = new ProjectReadMapper();

        projectService = new ProjectServiceImpl(projectRepository,taskRepository,mapper);

        task = new Task(1L, "task description",  false, 1L);
        task2 = new Task(2L, "task 2 description", true, 1L);
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
        lenient().when(taskRepository.findAllTaskByProjectId(anyLong())).thenReturn(taskList);
        ProjectDTO actual = projectService.findById(1L);
        assertEquals(projectDTO, actual);
    }

    @Test
    void testFindByIdNegative() throws RepositoryException, ServiceException {
        lenient().when(projectRepository.findById(anyLong())).thenReturn(project);
        lenient().when(taskRepository.findAllTaskByProjectId(anyLong())).thenReturn(taskList);
        ProjectDTO actual = projectService.findById(1L);
        assertNotEquals(new ProjectDTO(), actual);
    }

    @Test
    void testFindByIdException() throws RepositoryException {
        lenient().when(projectRepository.findById(anyLong())).thenThrow(RepositoryException.class);
        lenient().when(taskRepository.findAllTaskByProjectId(anyLong())).thenReturn(taskList);
        assertThrows(ServiceException.class, () -> projectService.findById(1L));
    }

    @Test
    void testCreatePositive() throws ServiceException, RepositoryException {
        lenient().when(projectRepository.create(any(Project.class))).thenReturn(project);
        lenient().when(taskRepository.create(any(Task.class))).thenReturn(task, task2);
        ProjectDTO actual = projectService.create(projectDTO);
        assertEquals(projectDTO, actual);
    }

    @Test
    void testCreateNegative() throws ServiceException, RepositoryException {
        lenient().when(projectRepository.create(any(Project.class))).thenReturn(project);
        lenient().when(taskRepository.create(any(Task.class))).thenReturn(task, task2);
        ProjectDTO actual = projectService.create(projectDTO);
        assertNotEquals(new ProjectDTO(), actual);
    }

    @Test
    void testCreateException() throws RepositoryException {
        lenient().when(projectRepository.create(any(Project.class))).thenThrow(RepositoryException.class);
        lenient().when(taskRepository.create(any(Task.class))).thenReturn(task, task2);
        assertThrows(ServiceException.class, () -> projectService.create(projectDTO));
    }

    @Test
    void testUpdatePositive() throws ServiceException, RepositoryException {
        lenient().when(projectRepository.update(any(Project.class))).thenReturn(project);
        lenient().when((taskRepository.findAllTaskByProjectId(anyLong()))).thenReturn(taskList);
        ProjectDTO actual = projectService.update(projectDTO);
        assertEquals(projectDTO, actual);
    }

    @Test
    void testUpdateNegative() throws ServiceException, RepositoryException {
        lenient().when(projectRepository.update(any(Project.class))).thenReturn(project);
        lenient().when((taskRepository.findAllTaskByProjectId(anyLong()))).thenReturn(taskList);
        ProjectDTO actual = projectService.update(projectDTO);
        assertNotEquals(new ProjectDTO(), actual);
    }

    @Test
    void testUpdateException() throws RepositoryException {
        lenient().when(projectRepository.update(any(Project.class))).thenThrow(RepositoryException.class);
        lenient().when((taskRepository.findAllTaskByProjectId(anyLong()))).thenReturn(taskList);
        assertThrows(ServiceException.class, () -> projectService.update(projectDTO));
    }

    @Test
    void testSearchByTitleOrDescriptionPositive() throws RepositoryException, ServiceException {
        lenient().when(projectRepository.searchByTitleOrDescription(anyString())).thenReturn(new ArrayList<>());
        List<ProjectDTO> actual = projectService.searchByTitleOrDescription("part");
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSearchByTitleOrDescriptionNegative() throws RepositoryException, ServiceException {
        ArrayList<Project> list = new ArrayList<>();
        list.add(project);
        lenient().when(projectRepository.searchByTitleOrDescription(anyString())).thenReturn(list);
        List<ProjectDTO> actual = projectService.searchByTitleOrDescription("part");
        assertNotEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSearchByTitleOrDescriptionException() throws RepositoryException {
        lenient().when(projectRepository.searchByTitleOrDescription(anyString())).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> projectService.searchByTitleOrDescription("part"));
    }

    @Test
    void testSortByTitlePositive() throws RepositoryException, ServiceException {
        lenient().when(projectRepository.sortByTitle(any(SortType.class))).thenReturn(new ArrayList<>());
        List<ProjectDTO> actual = projectService.sortByTitle(ASC);
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSortByTitleNegative() throws RepositoryException, ServiceException {
        ArrayList<Project> list = new ArrayList<>();
        list.add(project);
        lenient().when(projectRepository.sortByTitle(any(SortType.class))).thenReturn(list);
        List<ProjectDTO> actual = projectService.sortByTitle(ASC);
        assertNotEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSortByTitleException() throws RepositoryException {
        lenient().when(projectRepository.sortByTitle(any(SortType.class))).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> projectService.sortByTitle(ASC));
    }

    @Test
    void testSortByDateStartPositive() throws RepositoryException, ServiceException {
        lenient().when(projectRepository.sortByDateOfStart(any(SortType.class))).thenReturn(new ArrayList<>());
        List<ProjectDTO> actual = projectService.sortByDateStart(ASC);
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSortByDateStartNegative() throws RepositoryException, ServiceException {
        ArrayList<Project> list = new ArrayList<>();
        list.add(project);
        lenient().when(projectRepository.sortByDateOfStart(any(SortType.class))).thenReturn(list);
        List<ProjectDTO> actual = projectService.sortByDateStart(ASC);
        assertNotEquals(new ArrayList<>(), actual);
    }

    @Test
    void testSortByDateStartException() throws RepositoryException {
        lenient().when(projectRepository.sortByDateOfStart(any(SortType.class))).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> projectService.sortByDateStart(ASC));
    }

}