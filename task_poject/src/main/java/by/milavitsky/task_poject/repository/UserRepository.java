package by.milavitsky.task_poject.repository;

import by.milavitsky.task_poject.entity.User;

import java.util.List;

/**
 * Work with user in layer repository
 */
public interface UserRepository extends BaseRepository<User> {

    /**
     * Get count of all users from db
     * @return
     */
    long getCountOfUsers();
}
