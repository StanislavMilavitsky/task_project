package by.milavitsky.task_poject.extractor;

import by.milavitsky.task_poject.entity.Project;
import by.milavitsky.task_poject.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.milavitsky.task_poject.repository.constant.ConstantRepository.*;

@Slf4j
public class ProjectExtractor implements ResultSetExtractor<List<Project>> {

    @Override
    public List<Project> extractData(ResultSet set) throws DataAccessException {
        try {
            List<Project> projects = new ArrayList<>();
            while (set.next()) {
                Task task = Task.builder()
                        .id(set.getLong(ID))
                        .taskDescription(set.getString(TASK_DESCRIPTION))
                        .isDeleted(set.getBoolean(IS_DELETED)).build();
                long id = set.getLong(ID);
                Optional<Project> projectOptional = projects.stream().filter(c -> c.getId() == id).findFirst();
                if (projectOptional.isPresent()) {
                    List<Task> tasks = projectOptional.get().getTasks();
                    tasks.add(task);
                } else {
                    List<Task> tasks = new ArrayList<>();
                    tasks.add(task);
                    Project project = Project.builder()
                            .id(set.getLong(ID))
                            .title(set.getString(TITLE))
                            .projectDescription(set.getString(PROJECT_DESCRIPTION))
                            .budget(set.getBigDecimal(BUDGET))
                            .dateOfStart(set.getDate(DATE_OF_START).toLocalDate())
                            .dateOfEnd(set.getDate(DATE_OF_END).toLocalDate())
                            .isDeleted(set.getBoolean(IS_DELETED))
                            .build();
                    projects.add(project);
                }
            }
            return projects;
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
