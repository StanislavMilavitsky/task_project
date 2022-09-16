package by.milavitsky.task_poject.repository.impl;

import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.repository.ProjectRepository;
import by.milavitsky.task_poject.repository.entity.Project;
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
public class ProjectRepositoryImpl implements ProjectRepository {

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;

    @PostConstruct
    private void postConstruct(){
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("projects")
                .usingGeneratedKeyColumns(ID);
    }

    public static final String FIND_PROJECT_BY_ID_SQL = "SELECT pr.id, pr.title, pr.project_description, pr.budget," +
            " pr.date_of_start, pr.is_deleted, pr.date_of_end, t.id, t.task_description, t.is_deleted  FROM projects pr" +
            " JOIN project_has_task phs ON pr.id=phs.id_task " +
            "JOIN tasks t on phs.id_task = t.id WHERE pr.id = ?;";

    public static final String UPDATE_PROJECT_BY_ID_SQL = "UPDATE projects SET title = ?," +
            "project_description = ?," +
            "budget = ?," +
            "date_of_end = ? " +
            "WHERE projects = ?;";

    public static final String DELETE_PROJECT_BY_ID_SQL = "UPDATE projects SET is_deleted = true WHERE projects = ?;";

    public static final String FIND_ALL_PROJECTS_SQL = "SELECT pr.id, pr.title, pr.project_description, pr.budget," +
            " pr.date_of_start, pr.date_of_end, pr.is_deleted, t.id, t.task_description, t.is_deleted  FROM projects pr" +
            " JOIN project_has_task phs ON pr.id=phs.id_task " +
            "JOIN tasks t on phs.id_task = t.id;";

    @Override
    public Project create(Project project) throws RepositoryException {
        try{
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(TITLE, project.getTitle());
            parameters.put(PROJECT_DESCRIPTION, project.getProjectDescription());
            parameters.put(BUDGET, project.getBudget());
            parameters.put(DATE_OF_START, project.getDateOfStart());
            parameters.put(DATE_OF_END, project.getDateOfEnd());
            parameters.put(IS_DELETED, false);

            Number id = jdbcInsert.executeAndReturnKey(parameters);
            project.setId(id.longValue());
            return project;
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Create project by title name=%s exception sql!", project.getTitle());
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public Project findById(Long id) throws RepositoryException {
        try {
            return jdbcTemplate.queryForObject(FIND_PROJECT_BY_ID_SQL, new BeanPropertyRowMapper<>(Project.class), id);
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Read project by id=%d exception sql!", id);
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public Project update(Project project) throws RepositoryException {
        try {
            int rows = jdbcTemplate.update(UPDATE_PROJECT_BY_ID_SQL, project.getProjectDescription(), project.getBudget(), project.getDateOfEnd());
            return rows > 0L ? findById(project.getId()) : null;
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Update project by id=%d exception sql!", project.getId());
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public void delete(Long id) throws RepositoryException { try{
        jdbcTemplate.update(DELETE_PROJECT_BY_ID_SQL, id);
    } catch (DataAccessException exception){
        String exceptionMessage = String.format("Delete project by id=%d exception sql!", id);
        log.error(exceptionMessage, exception);
        throw new RepositoryException(exceptionMessage, exception);
    }
    }

    //SELECT pr.id, pr.title, pr.project_description, pr.budget," +
    //            " pr.date_of_start, pr.is_deleted, t.id, t.task_description, t.is_deleted  FROM projects pr" +
    //            " JOIN project_has_task phs ON pr.id=phs.id_task " +
    //            "JOIN tasks t on phs.id_task = t.id;
    @Override
    public List<Project> findAll() {
        return jdbcTemplate.query(FIND_ALL_PROJECTS_SQL,new BeanPropertyRowMapper<>(Project.class));
    }
}
