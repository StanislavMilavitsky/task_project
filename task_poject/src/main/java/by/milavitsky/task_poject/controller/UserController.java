package by.milavitsky.task_poject.controller;

import by.milavitsky.task_poject.entity.User;
import by.milavitsky.task_poject.exception.ControllerException;
import by.milavitsky.task_poject.exception.IncorrectArgumentException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.service.UserService;
import by.milavitsky.task_poject.validation.group.CreateAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * User RestController.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController extends PageController<User> {

    private final UserService userService;

    /**
     * Find user by id use method from service layer
     *
     * @param id user
     * @return entity user
     * @throws ServiceException    if cant find user
     * @throws ControllerException if negative id
     */
    @GetMapping("/{id}/find-by-id")
    @PostAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<User> findById(@PathVariable(name = "id") Long id) throws ControllerException, ServiceException {
        if (id > 0) {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } else {
            log.error("Negative id exception");
            throw new ControllerException("Negative id exception");
        }
    }

    /**
     * Create user
     *
     * @param user is entity user
     * @param bindingResult errors of validation
     * @return created user
     * @throws ControllerException if negative id
     * @throws ServiceException    the service exception
     */

    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody @Valid User user, BindingResult bindingResult) throws ControllerException, ServiceException {
        if (bindingResult.hasErrors()) {
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            User result = userService.create(user);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Update user. Mark the fields that are not specified for updating null.
     *
     * @param user the entity user
     * @return the response entity
     * @throws ServiceException    the service exception
     * @throws ControllerException if entity fields not valid
     */
    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody @Valid @Validated({CreateAction.class}) User user, BindingResult bindingResult) throws ServiceException, ControllerException {
        if (bindingResult.hasErrors()) {
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            User result = userService.update(user);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Delete user by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException    the service exception
     * @throws ControllerException if id is incorrect
     */
    @DeleteMapping("/{id}/delete")
    @PostAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException {
        if (id > 0) {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new ControllerException("Negative id exception");
        }
    }

    /**
     * Find all users use method from service layer
     *
     * @param page page
     * @param size size of page
     * @return list of users
     * @throws ServiceException           if cant find users
     * @throws IncorrectArgumentException if incorrect argument
     */
    @Override
    @GetMapping
    @PostAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PagedModel<User>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) throws ServiceException, IncorrectArgumentException {
        List<User> tags = userService.findAll(page, size);
        long count = userService.count();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, count);
        List<Link> linkList = buildLink(page, size, (int) pageMetadata.getTotalPages());
        PagedModel<User> pagedModel = PagedModel.of(tags, pageMetadata, linkList);
        return ResponseEntity.ok(pagedModel);
    }

}
