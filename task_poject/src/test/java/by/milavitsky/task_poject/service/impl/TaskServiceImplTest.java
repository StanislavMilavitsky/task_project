package by.milavitsky.task_poject.service.impl;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.exception.ServiceException;

import by.milavitsky.task_poject.repository.TaskRepository;
import by.milavitsky.task_poject.entity.Task;

import by.milavitsky.task_poject.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@SpringBootTest
class TaskServiceImplTest {

    /**
     * Initialisation test
     */

    @Mock
    TaskRepository taskRepository;
    TaskService taskService;
    Task task;

    @BeforeEach
    void setUp() {
        task = new Task(1L, "description",  false);

        taskService = new TaskServiceImpl(taskRepository);

    }

    /**
     * Tests methods
     */

    @Test
    void testFindByIdPositive() throws RepositoryException, ServiceException {
        lenient().when(taskRepository.findById(anyLong())).thenReturn(task);
        Task actual = taskService.findById(1L);
        assertEquals(task, actual);
    }

    @Test
    void testFindByIdNegative() throws RepositoryException, ServiceException {
        Task actual = taskService.findById(2L);
        assertNotEquals(task, actual);
    }

    @Test
    void testFindException() throws RepositoryException {
        lenient().when(taskRepository.findById(anyLong())).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> taskService.findById(1L));
    }

    @Test
    void testCreatePositive() throws ServiceException, RepositoryException {
        lenient().when(taskRepository.create(any(Task.class))).thenReturn(task);
        Task actual = taskService.create(task);
        assertEquals(task, actual);
    }

    @Test
    void testCreateNegative() throws ServiceException, RepositoryException {
        lenient().when(taskRepository.create(any(Task.class))).thenReturn(task);
        Task actual = taskService.create(task);
        assertNotEquals(new ProjectDTO(), actual);
    }

    @Test
    void testCreateException() throws RepositoryException {
        lenient().when(taskRepository.create(any(Task .class))).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> taskService.create(task));
    }

    @Test
    void testUpdatePositive() throws ServiceException, RepositoryException {
        lenient().when(taskRepository.update(any(Task.class))).thenReturn(task);
        lenient().when((taskRepository.findById(anyLong()))).thenReturn(task);
        Task actual = taskService.update(task);
        assertEquals(task, actual);
    }

    @Test
    void testUpdateNegative() throws ServiceException, RepositoryException {
        lenient().when(taskRepository.update(any(Task.class))).thenReturn(task);
        lenient().when((taskRepository.findById(anyLong()))).thenReturn(task);
        Task actual = taskService.update(task);
        assertNotEquals(new ProjectDTO(), actual);
    }

    @Test
    void testUpdateException() throws RepositoryException {
        lenient().when(taskRepository.update(any(Task.class))).thenThrow(RepositoryException.class);
        assertThrows(ServiceException.class, () -> taskService.update(task));
    }
}