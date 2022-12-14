package by.milavitsky.task_poject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Project implements Serializable {

    @Positive(message = "Should be positive")
    private Long id;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 2, max = 20, message = "Title should be between 2 and 30 characters")
    private String title;

    @Size(min = 2, max = 100, message = "Title should be between 2 and 100 characters")
    private String projectDescription;

    @Positive(message = "Budget should be positive")
    private BigDecimal budget;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.s")
    private LocalDate dateOfStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.s")
    private LocalDate dateOfEnd;

    private Boolean isDeleted;

    private List<Task> tasks;
}
