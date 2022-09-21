package by.milavitsky.task_poject.repository.impl;


import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.repository.ProjectRepository;
import by.milavitsky.task_poject.entity.Project;
import javafx.scene.control.TableColumn;
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
    private void postConstruct() {
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("projects")
                .usingGeneratedKeyColumns(ID);
    }



    public static final String FIND_PROJECT_BY_ID_SQL = "SELECT pr.id, pr.title, pr.project_description, pr.budget, pr.date_of_start, pr.is_deleted, pr.date_of_end" +
            "  FROM projects pr WHERE pr.id = ?;";

    public static final String UPDATE_PROJECT_BY_ID_SQL = "UPDATE projects SET title = ?," +
            "project_description = ?," +
            "budget = ?," +
            "date_of_end = ? " +
            "WHERE projects = ?;";

    public static final String DELETE_PROJECT_BY_ID_SQL = "UPDATE projects SET is_deleted = true WHERE projects = ?;";

    public static final String FIND_ALL_PROJECTS_SQL = "SELECT pr.id, pr.title, pr.project_description, pr.budget, pr.date_of_start, pr.date_of_end, pr.is_deleted" +
            " FROM projects pr" +
            " LIMIT ? OFFSET ?;";

    private static final String SELECT_BY_TITLE_OR_DESCRIPTION_SQL = "SELECT pr.id, pr.title, pr.project_description, pr.budget, pr.date_of_start," +
            " pr.date_of_end, pr.is_deleted FROM projects pr WHERE pr.title LIKE ? OR pr.project_description LIKE ?";

    private static final String SORT_BY_TITLE_SQL = "SELECT pr.id, title, project_description, budget, date_of_start, date_of_end, is_deleted" +
            " FROM projects pr ORDER BY pr.title;";

    private static final String SORT_BY_DATE_SQL_START = "SELECT pr.id, title, project_description, budget, date_of_start, date_of_end, is_deleted " +
            "FROM projects pr ORDER BY pr.date_of_start;";

    private static final String SORT_BY_DATE_SQL_END = "SELECT pr.id, title, project_description, budget, date_of_start, date_of_end, is_deleted " +
            "FROM projects pr ORDER BY pr.date_of_end;";

    private static final String COUNT_OF_ALL_PROJECTS = "SELECT count(*) FROM projects;";

    private static final String COUNT_OF_ALL_PROJECTS_NOT_DELETED = "SELECT count(*) FROM projects WHERE is_deleted = false;";

    private static final String FIND_ALL_PROJECTS_NOT_DELETED_SQL = "SELECT pr.id, pr.title, pr.project_description, pr.budget, pr.date_of_start, pr.date_of_end, pr.is_deleted FROM projects pr WHERE pr.is_deleted = false LIMIT ? OFFSET ?;";

    @Override
    public Project create(Project project) throws RepositoryException {
        try {
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
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Create project by title name=%s exception sql!", project.getTitle());
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public Project findById(Long id) throws RepositoryException {
        try {
            return jdbcTemplate.queryForObject(FIND_PROJECT_BY_ID_SQL, new BeanPropertyRowMapper<>(Project.class), id);
        } catch (DataAccessException exception) {
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
    public void delete(Long id) throws RepositoryException {
        try {
            int rows = jdbcTemplate.update(DELETE_PROJECT_BY_ID_SQL, id);
            if (rows < 0) {
                String message = String.format("Entity by id=%d was deleted!", id);
                log.error(message);
                throw new RepositoryException(message);
            }
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Delete project by id=%d exception sql!", id);
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public List<Project> findAll(int offset, int limit) {
        return jdbcTemplate.query(FIND_ALL_PROJECTS_SQL,new BeanPropertyRowMapper<>(Project.class), limit, offset);
    }

    @Override
    public List<Project> searchByTitleOrDescription(String part) throws RepositoryException {
        try {
            String sqlPart = PERCENT + part + PERCENT;
            return jdbcTemplate.query(SELECT_BY_TITLE_OR_DESCRIPTION_SQL, new BeanPropertyRowMapper<>(Project.class), sqlPart, sqlPart);
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Find project by word=%s exception sql!", part);
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public List<Project> sortByTitle(TableColumn.SortType sortType) throws RepositoryException {
        try {
            StringBuilder builder = new StringBuilder(SORT_BY_TITLE_SQL);
            if (sortType == TableColumn.SortType.DESCENDING) {
                builder.append(TableColumn.SortType.DESCENDING.name());
            }
            return jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(Project.class));
        } catch (DataAccessException exception) {
            String exceptionMessage = "Sort projects by title exception sql";
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public List<Project> sortByDateOfStart(TableColumn.SortType sortType) throws RepositoryException {
        try {
            StringBuilder builder = new StringBuilder(SORT_BY_DATE_SQL_START);
            if (sortType == TableColumn.SortType.DESCENDING) {
                builder.append(TableColumn.SortType.DESCENDING.name());
            }
            return jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(Project.class));
        } catch (DataAccessException exception) {
            String exceptionMessage = "Sort project by date of start exception sql";
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public List<Project> sortByDateOfEnd(TableColumn.SortType sortType) throws RepositoryException {
        try {
            StringBuilder builder = new StringBuilder(SORT_BY_DATE_SQL_END);
            if (sortType == TableColumn.SortType.DESCENDING) {
                builder.append(TableColumn.SortType.DESCENDING.name());
            }
            return jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(Project.class));
        } catch (DataAccessException exception) {
            String exceptionMessage = "Sort project by date of end exception sql";
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public long countOfProjects() {
        return jdbcTemplate.queryForObject(COUNT_OF_ALL_PROJECTS, Long.class);
    }

    @Override
    public long countOfProjectsNotDeleted() {
        return jdbcTemplate.queryForObject(COUNT_OF_ALL_PROJECTS_NOT_DELETED, Long.class);
    }

    @Override
    public List<Project> findAllNotDeleted(int offset, int limit) {
        return jdbcTemplate.query(FIND_ALL_PROJECTS_NOT_DELETED_SQL,new BeanPropertyRowMapper<>(Project.class), limit, offset);
    }
}
