package by.milavitsky.task_poject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Task implements Serializable {

    @Positive(message = "Should be positive")
    private Long id;

    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    private String taskDescription;

    private Boolean isDeleted;
}