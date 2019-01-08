package ru.geekbrains.pocket.backend.resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import ru.geekbrains.pocket.backend.controller.rest.RegisterRestController;
import ru.geekbrains.pocket.backend.domain.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

//http://spring-projects.ru/guides/tutorials-bookmarks/
//ResourceSupport - тип Spring HATEOAS, он аккумулирует объекты Link, которые описывают ресурсы.
public class UserResource extends ResourceSupport {

    private final User user;

    public UserResource(User user) {
        this.user = user;
        this.add(new Link("uri"));
        this.add(linkTo(RegisterRestController.class, user.getUsername()).withRel("username"));
        this.add(linkTo(methodOn(RegisterRestController.class, user.getUsername()).getUserById(user.getId())).withSelfRel());
    }

    public User getUser() {
        return user;
    }
}