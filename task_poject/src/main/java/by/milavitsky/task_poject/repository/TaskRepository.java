package by.milavitsky.task_poject.repository;

import by.milavitsky.task_poject.entity.Task;

import java.util.List;

/**
 * Work with task in layer repository
 */
public interface TaskRepository extends BaseRepository <Task> {
    List<Task> findAll();
}
