package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.mapper.Mapper;
import by.milavitsky.task_poject.mapper.ProjectReadMapper;
import by.milavitsky.task_poject.repository.ProjectRepository;
import by.milavitsky.task_poject.repository.entity.Project;
import by.milavitsky.task_poject.repository.entity.Task;
import by.milavitsky.task_poject.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@SpringBootTest
class ProjectServiceImplTest {

    /**
     * Initialisation test
     */

    @Mock
    ProjectService projectService;
    ProjectRepository projectRepository;
    Project project;
    ProjectDTO projectDTO;
    Mapper mapper;
    List<Task> taskList;

    @BeforeEach
    void setUp() {
        mapper = new ProjectReadMapper();

        projectService = new ProjectServiceImpl(projectRepository, mapper);

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
    void testFindByIdNegative() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findAll() {
    }
}