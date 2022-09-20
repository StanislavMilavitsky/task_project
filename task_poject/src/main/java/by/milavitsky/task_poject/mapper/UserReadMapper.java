package by.milavitsky.task_poject.mapper;

import by.milavitsky.task_poject.dto.UserDTO;
import by.milavitsky.task_poject.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserReadMapper implements Mapper<UserDTO, User> {
    @Override
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getDateOfRegistration().toString(),
                user.getRole(),
                user.getIsDeleted()
        );
    }

    @Override
    public User fromDTO(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getUserName(),
                userDTO.getPassword(),
                LocalDate.now(),
                userDTO.getRole(),
                false

        );
    }
}
