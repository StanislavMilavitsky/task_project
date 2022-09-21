package by.milavitsky.task_poject.repository;

import by.milavitsky.task_poject.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Work with user in layer repository
 */
public interface UserRepository extends BaseRepository<User> {

    /**
     * Get count of all users from db
     * @return count of users
     */
    long countOfUsers();

    /**
     * Get user by username
     * @param username is username
     * @return user from db
     */
    Optional<User> findByUsername(String username);
}
