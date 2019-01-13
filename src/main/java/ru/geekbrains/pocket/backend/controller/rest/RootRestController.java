package ru.geekbrains.pocket.backend.controller.rest;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class RootRestController {

    @GetMapping
    ResourceSupport index() {
        ResourceSupport rootResource = new ResourceSupport();
        rootResource.add(linkTo(methodOn(UserRestController.class).getAllUsers()).withRel("users"));
        rootResource.add(linkTo(methodOn(UserRestController.class).getUserByName("test")).withRel("getUserByName(\"test\")"));
        rootResource.add(linkTo(methodOn(UserRestController.class).getUserByName("test")).withRel("getUserByName(\"ivan\")"));
        rootResource.add(linkTo(methodOn(RegisterRestController.class).getUserByName("test")).withRel("RegisterRestController.getUserByName(\"test\") whit @CrossOrigin"));
        return rootResource;
    }

}
