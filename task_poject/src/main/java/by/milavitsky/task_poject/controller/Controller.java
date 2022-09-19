package by.milavitsky.task_poject.controller;

import by.milavitsky.task_poject.exception.ControllerException;
import by.milavitsky.task_poject.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface Controller <T> {
    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if id is incorrect
     */

    public ResponseEntity<T> findById(@PathVariable(name = "id") Long id) throws ControllerException, ServiceException;


    /**
     * Add employee.
     *
     * @param t the employee dto
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if entity fields not valid
     */

    public ResponseEntity<T> create(@RequestBody @Valid T t, BindingResult bindingResult) throws ControllerException, ServiceException;;

    /**
     * Update employee. Mark the fields that are not specified for updating null.
     *
     * @param t the employee dto
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if entity fields not valid
     */

    public ResponseEntity<T> update(@RequestBody @Valid T t,
                                              BindingResult bindingResult) throws ServiceException, ControllerException;

    /**
     * Delete employee by id.
     *
     * @param id the id
     * @return the response entity
     * @throws ServiceException the service exception
     * @throws ControllerException if id is incorrect
     */

    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) throws ServiceException, ControllerException;

    /**
     * Get default message by validate exception
     *
     * @param bindingResult exceptions of validate
     * @return string default message of exception
     */
    default String bindingResultHandler(BindingResult bindingResult){
        return bindingResult.getAllErrors().get(0).getDefaultMessage();
    }
}
