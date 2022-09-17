package by.milavitsky.task_poject.controller;

import by.milavitsky.task_poject.exception.IncorrectArgumentException;
import by.milavitsky.task_poject.exception.ServiceException;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create dynamic link for the page with entity
 *
 * @param <T> entity that must be on page
 */
public abstract class CommonController<T> {
    public abstract ResponseEntity<PagedModel<T>> findAll(int page, int size)
            throws ServiceException, IncorrectArgumentException;

    protected List<Link> buildLink(int page, int size, int maxPage)
            throws ServiceException, IncorrectArgumentException {
        List<Link> linkList = new ArrayList<>();
        Link self = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder
                        .methodOn(this.getClass())
                        .findAll(page, size)
        ).withRel("self");
        linkList.add(self);
        if (page > 1) {
            Link previous = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder
                            .methodOn(this.getClass())
                            .findAll(page - 1, size)
            ).withRel("previous");
            linkList.add(previous);
        }
        if (maxPage > page) {
            Link next = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder
                            .methodOn(this.getClass())
                            .findAll(page + 1, size)
            ).withRel("next");
            linkList.add(next);
        }
        return linkList;
    }
}
