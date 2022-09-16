package by.milavitsky.task_poject.mapper;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.repository.entity.Project;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProjectReadMapper implements Mapper<ProjectDTO, Project> {
    @Override
    public ProjectDTO toDto(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getTitle(),
                project.getProjectDescription(),
                project.getBudget(),
                project.getDateOfStart().toString(),
                project.getDateOfEnd().toString(),
                project.getIsDeleted(),
                project.getTasks()
        );
    }

    @Override
    public Project fromDto(ProjectDTO projectDTO) {
        return new Project(
                projectDTO.getId(),
                projectDTO.getTitle(),
                projectDTO.getProjectDescription(),
                projectDTO.getBudget(),
                LocalDate.parse(projectDTO.getDateOfStart()),
                LocalDate.parse(projectDTO.getDateOfEnd()),
                projectDTO.getIsDeleted(),
                projectDTO.getTasks()
        );
    }
}
