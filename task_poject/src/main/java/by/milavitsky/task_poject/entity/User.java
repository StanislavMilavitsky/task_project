package by.milavitsky.task_poject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements Serializable {

    @Positive(message = "Should be positive")
    private Long id;

    @NotEmpty(message = "Username should not be empty")
    @Size(min = 2, max = 30, message = "Username should be between 2 and 50 characters")
    private String userName;

    private String password;

    private LocalDate dateOfRegistration;

    private String role;

    private Boolean isDeleted;
}
