package by.milavitsky.task_poject.controller;

import by.milavitsky.task_poject.dto.UserDTO;
import by.milavitsky.task_poject.exception.ControllerException;
import by.milavitsky.task_poject.exception.IncorrectArgumentException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
public class UserController extends CommonController<UserDTO> {

    private final UserService userService;

    /**
     * Find user by id use method from service layer
     *
     * @param id user
     * @return entity user
     * @throws ServiceException    if cant find user
     * @throws ControllerException if negative id
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable(name = "id") Long id) throws ControllerException, ServiceException {
        if (id > 0) {
            UserDTO userDTO = userService.findById(id);
            return ResponseEntity.ok(userDTO);
        } else {
            log.error("Negative id exception");
            throw new ControllerException("Negative id exception");
        }
    }

    /**
     * Create user
     *
     * @param userDTO is entity user
     * @param bindingResult errors of validation
     * @return created user
     * @throws ControllerException if negative id
     * @throws ServiceException    the service exception
     */

    @PostMapping()
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws ControllerException, ServiceException {
        if (bindingResult.hasErrors()) {
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            UserDTO result = userService.create(userDTO);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Update user. Mark the fields that are not specified for updating null.
     *
     * @param userDTO the entity user
     * @return the response entity
     * @throws ServiceException    the service exception
     * @throws ControllerException if entity fields not valid
     */
    @PutMapping()
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) throws ServiceException, ControllerException {
        if (bindingResult.hasErrors()) {
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            UserDTO result = userService.update(userDTO);
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
    @DeleteMapping("/{id}")
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
    public ResponseEntity<PagedModel<UserDTO>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size
    ) throws ServiceException, IncorrectArgumentException {
        List<UserDTO> tags = userService.findAll(page, size);
        long count = userService.count();
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, count);
        List<Link> linkList = buildLink(page, size, (int) pageMetadata.getTotalPages());
        PagedModel<UserDTO> pagedModel = PagedModel.of(tags, pageMetadata, linkList);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Get default message by validate exception
     *
     * @param bindingResult exceptions of validate
     * @return string default message of exception
     */
    private String bindingResultHandler(BindingResult bindingResult) {
        return bindingResult.getAllErrors().get(0).getDefaultMessage();
    }

}
