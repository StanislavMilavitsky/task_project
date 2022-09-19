package by.milavitsky.task_poject.repository.impl;

import by.milavitsky.task_poject.entity.User;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.repository.TaskRepository;
import by.milavitsky.task_poject.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.milavitsky.task_poject.repository.constant.ConstantRepository.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    private void postConstruct(){
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("tasks")
                .usingGeneratedKeyColumns(ID);
    }

    public static final String FIND_TASK_BY_ID_SQL = "SELECT ts.id, task_description, is_deleted FROM tasks ts WHERE ts.id = ?;";

    public static final String UPDATE_TASK_BY_ID_SQL = "UPDATE tasks SET task_description = ? WHERE id =?;";

    public static final String DELETE_TASK_BY_ID_SQL = "UPDATE tasks SET is_deleted = true WHERE  id = ?;";

    public static final String FIND_ALL_TASKS_SQL = "SELECT ts.id, task_description, is_deleted FROM tasks ts;";

    @Override
    public Task create(Task task) throws RepositoryException {
        try{
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(TASK_DESCRIPTION, task.getTaskDescription());
            parameters.put(IS_DELETED, false);
            Number id = jdbcInsert.executeAndReturnKey(parameters);
            task.setId(id.longValue());
            return task;
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Create task exception sql!");
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public Task findById(Long id) throws RepositoryException {
        try {
            return jdbcTemplate.queryForObject(FIND_TASK_BY_ID_SQL, new BeanPropertyRowMapper<>(Task.class), id);
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Read task by id=%d exception sql!", id);
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public Task update(Task task) throws RepositoryException {
            try {
                int rows = jdbcTemplate.update(UPDATE_TASK_BY_ID_SQL, task.getTaskDescription(), task.getId());
                return rows > 0L ? findById(task.getId()) : null;
            } catch (DataAccessException exception) {
                String exceptionMessage = String.format("Update task by id=%d exception sql!", task.getId());
                log.error(exceptionMessage, exception);
                throw new RepositoryException(exceptionMessage, exception);
            }
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        try{
            int rows = jdbcTemplate.update(DELETE_TASK_BY_ID_SQL, id);
            if (rows < 0) {
                String message = String.format("Entity by id=%d was deleted!", id);
                log.error(message);
                throw new RepositoryException(message);
            }
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Delete task by id=%d exception sql!", id);
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public List<User> findAll(int offset, int limit) {
        return null;
    }

    @Override
    public List<Task> findAll() {
        return jdbcTemplate.query(FIND_ALL_TASKS_SQL, (rs, rowNum) -> new Task(
                rs.getLong(ID),
                rs.getString(TASK_DESCRIPTION),
                rs.getBoolean(IS_DELETED)
        ));
    }
}
