package by.milavitsky.task_poject.controller;

import by.milavitsky.task_poject.dto.ProjectDTO;
import by.milavitsky.task_poject.entity.SortType;
import by.milavitsky.task_poject.exception.IncorrectArgumentException;
import by.milavitsky.task_poject.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.exception.ControllerException;

import javax.validation.Valid;
import java.util.List;


/**
 * Project Rest Controller
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController extends PageController<ProjectDTO> {

    private final ProjectService projectService;

    /**
     * Find project by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException  the service exception
     * @throws ControllerException if id is incorrect
     */
    @GetMapping("/{id}/find-by-id")
    public ResponseEntity<ProjectDTO> findById(@PathVariable(name = "id") Long id) throws ControllerException, ServiceException {
        if (id > 0) {
            ProjectDTO projectDTO = projectService.findById(id);
            return ResponseEntity.ok(projectDTO);
        } else {
            log.error("Negative id exception");
            throw new ControllerException("Negative id exception");
        }
    }

    /**
     * Add project.
     *
     * @param projectDTO the project dto
     * @return the response entity
     * @throws ServiceException    the service exception
     * @throws ControllerException if entity fields not valid
     */

    @PostMapping("/create")
    public ResponseEntity<ProjectDTO> create(@RequestBody @Valid ProjectDTO projectDTO, BindingResult bindingResult)
            throws ServiceException, ControllerException {
        if (bindingResult.hasErrors()) {
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            ProjectDTO result = projectService.create(projectDTO);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Update project. Mark the fields that are not specified for updating null.
     *
     * @param projectDTO the project dto
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if entity fields not valid
     */
    @PutMapping("/update")
    public ResponseEntity<ProjectDTO> update(@RequestBody @Valid ProjectDTO projectDTO,
                                             BindingResult bindingResult) throws ServiceException, ControllerException {
        if (bindingResult.hasErrors()) {
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            ProjectDTO result = projectService.update(projectDTO);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Delete project by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if id is incorrect
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException {
        if (id > 0) {
            projectService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new ControllerException("Negative id exception");
        }

    }

    /**
     * Find all projects
     * @param page the page
     * @param size count on page
     * @return projects
     * @throws ServiceException the service excaption
     * @throws IncorrectArgumentException incorrect argument
     */
    @Override
    @GetMapping("/find-all-projects-admin")
    @PostAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PagedModel<ProjectDTO>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) throws ServiceException, IncorrectArgumentException {
        List<ProjectDTO> tags = projectService.findAll(page, size);
        long count = projectService.count();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, count);
        List<Link> linkList = buildLink(page, size, (int) pageMetadata.getTotalPages());
        PagedModel<ProjectDTO> pagedModel = PagedModel.of(tags, pageMetadata, linkList);
        return ResponseEntity.ok(pagedModel);
    }


    @GetMapping("/find-all-projects")
    public ResponseEntity<PagedModel<ProjectDTO>> findAllByUser(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) throws ServiceException, IncorrectArgumentException {
        List<ProjectDTO> tags = projectService.findAllByUser(page, size);
        long count = projectService.count();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, count);
        List<Link> linkList = buildLink(page, size, (int) pageMetadata.getTotalPages());
        PagedModel<ProjectDTO> pagedModel = PagedModel.of(tags, pageMetadata, linkList);
        return ResponseEntity.ok(pagedModel);
    }
    /**
     * Search project by title or description part.
     *
     * @param part the part
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/search-part")
    public ResponseEntity<List<ProjectDTO>> searchByNameOrDesc(@RequestParam(name = "part") String part)
            throws ServiceException {
        List<ProjectDTO> projectDTO = projectService.searchByTitleOrDescription(part);
        return ResponseEntity.ok(projectDTO);
    }

    /**
     * Sort project by title.
     *
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort-by-title")
    public ResponseEntity<List<ProjectDTO>> sortByName(@RequestParam(name = "sort") SortType sortType) throws ServiceException {
        List<ProjectDTO> projectDTO = projectService.sortByTitle(sortType);
        return ResponseEntity.ok(projectDTO);
    }

    /**
     * Sort projects by date of start.
     *
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort-by-date-start")
    public ResponseEntity<List<ProjectDTO>> sortByDateStart(@RequestParam(name = "sort") SortType sortType) throws ServiceException {
        List<ProjectDTO> projectDTO = projectService.sortByDateStart(sortType);
        return ResponseEntity.ok(projectDTO);
    }

    /**
     * Sort projects by date of end.
     *
     * @return the response entity
     * @throws ServiceException the service exception
     */
    @GetMapping("/sort-by-date-end")
    public ResponseEntity<List<ProjectDTO>> sortByDateEnd(@RequestParam(name = "sort") SortType sortType) throws ServiceException {
        List<ProjectDTO> projectDTO = projectService.sortByDateEnd(sortType);
        return ResponseEntity.ok(projectDTO);
    }

}


