package by.milavitsky.task_poject.controller;

import by.milavitsky.task_poject.exception.ControllerException;
import by.milavitsky.task_poject.exception.ServiceException;
import by.milavitsky.task_poject.entity.Task;
import by.milavitsky.task_poject.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Task Rest Controller.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    /**
     * Find task by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if id is incorrect
     */
    @GetMapping("/{id}/find-by-id")
    public ResponseEntity<Task> findById(@PathVariable(name = "id") Long id) throws ControllerException, ServiceException {
        if (id > 0){
            Task task = taskService.findById(id);
            return ResponseEntity.ok(task);
        } else {
            log.error("Negative id exception");
            throw  new ControllerException("Negative id exception");
        }
    }

    /**
     * Update task. Mark the fields that are not specified for updating null.
     *
     * @param task the entity
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if entity fields not valid
     */
    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody @Valid Task task,
                                              BindingResult bindingResult) throws ServiceException, ControllerException{
        if (bindingResult.hasErrors()){
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            Task result = taskService.update(task);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Delete task by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if id is incorrect
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException {
        if (id > 0) {
            taskService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new ControllerException("Negative id exception");
        }

    }

    /**
     * Create task
     *
     * @param task is entity task
     * @param bindingResult errors of validation
     * @return created task
     * @throws ControllerException if negative id
     * @throws ServiceException    the service exception
     */

    @PostMapping("/create")
    public ResponseEntity<Task> create(@RequestBody @Valid Task task, BindingResult bindingResult) throws ControllerException, ServiceException {
        if (bindingResult.hasErrors()) {
            log.error(bindingResultHandler(bindingResult));
            throw new ControllerException(bindingResultHandler(bindingResult));
        } else {
            Task result = taskService.create(task);
            return ResponseEntity.ok(result);
        }
    }

    /**
     * Get default message by validate exception
     *
     * @param bindingResult exceptions of validate
     * @return string default message of exception
     */
    private String bindingResultHandler(BindingResult bindingResult){
        return bindingResult.getAllErrors().get(0).getDefaultMessage();
    }
}
