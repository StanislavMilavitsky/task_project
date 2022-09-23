package by.milavitsky.task_poject.repository;

import by.milavitsky.task_poject.entity.Task;

import java.util.List;

/**
 * Work with task in layer repository
 */
public interface TaskRepository extends BaseRepository <Task> {

    /**
     * Find all tasks
     * @return list of tasks
     */
    List<Task> findAll();

    /**
     * Find all tasks by id project
     * @param id project
     * @return list of tasks
     */
    List<Task> findAllTaskByProjectId(Long id);

    /**
     * Find all tasks by id project not deleted
     * @param id project
     * @return list of tasks
     */
    List<Task> findAllTaskByProjectIdNotDeleted(Long id);
}
