package by.milavitsky.task_poject.service;

import by.milavitsky.task_poject.entity.Task;

import java.util.List;

public interface TaskService extends BaseService<Task> {
    /**
     * Use method findAll in repository layer
     * @return list of all entity
     */
    List<Task> findAll();
}
