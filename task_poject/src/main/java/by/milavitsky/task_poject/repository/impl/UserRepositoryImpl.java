package by.milavitsky.task_poject.repository.impl;


import by.milavitsky.task_poject.entity.User;
import by.milavitsky.task_poject.exception.RepositoryException;
import by.milavitsky.task_poject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.milavitsky.task_poject.repository.constant.ConstantRepository.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;
    @PostConstruct
    private void postConstruct(){
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns(ID);
    }

    private static final String FIND_USER_BY_ID_SQL = "SELECT us.id," +
            " username," +
            " password," +
            " date_of_registration," +
            " role, is_deleted" +
            " FROM users us" +
            " WHERE us.id = ?;";

    private static final String UPDATE_USER_BY_ID_SQL = "UPDATE users" +
            " SET username = ?," +
            " password = ?" +
            "WHERE id = ?;";

    public static final String DELETE_USER_BY_ID_SQL = "UPDATE users " +
            " SET is_deleted = true" +
            " WHERE id = ?;";

    public static final String FIND_ALL_USERS_SQL = "SELECT us.id," +
            " username," +
            " password," +
            " date_of_registration," +
            " role, is_deleted FROM users us;";

    @Override
    public User create(User user) throws RepositoryException {
        try{
            Map<String, Object> parameters = new HashMap<>();
            parameters.put(USERNAME, user.getUserName());
            parameters.put(PASSWORD, user.getPassword());
            parameters.put(DATE_OF_REGISTRATION, LocalDate.now());
            parameters.put(ROLE, user.getRole());
            parameters.put(IS_DELETED, false);
            Number id = jdbcInsert.executeAndReturnKey(parameters);
            user.setId(id.longValue());
            return user;
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Create user by Last username=%s exception sql!", user.getUserName());
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public User findById(Long id) throws RepositoryException {
        try {
            return jdbcTemplate.queryForObject(FIND_USER_BY_ID_SQL, new BeanPropertyRowMapper<>(User.class), id);
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Read user by id=%d exception sql!", id);
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public User update(User user) throws RepositoryException {
        try {
            int rows = jdbcTemplate.update(UPDATE_USER_BY_ID_SQL, user.getUserName(), user.getPassword(),
               user.getId());
            return rows > 0L ? findById(user.getId()) : null;
        } catch (DataAccessException exception) {
            String exceptionMessage = String.format("Update user by id=%d exception sql!", user.getId());
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        try{
            jdbcTemplate.update(DELETE_USER_BY_ID_SQL, id);
        } catch (DataAccessException exception){
            String exceptionMessage = String.format("Delete user by id=%d exception sql!", id);
            log.error(exceptionMessage, exception);
            throw new RepositoryException(exceptionMessage, exception);
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL_USERS_SQL, (rs, rowNum) -> new User(
                rs.getLong(ID),
                rs.getString(USERNAME),
                rs.getString(PASSWORD),
                rs.getDate(DATE_OF_REGISTRATION).toLocalDate(),
                rs.getString(ROLE),
                rs.getBoolean(IS_DELETED)
        ));
    }
}
