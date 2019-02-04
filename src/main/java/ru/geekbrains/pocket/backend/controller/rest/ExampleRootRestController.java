package ru.geekbrains.pocket.backend.controller.rest;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.pocket.backend.response.GreetingResponse;

import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
class ExampleRootRestController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ExampleRootRestController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping
    ResourceSupport index() {
        ResourceSupport rootResource = new ResourceSupport();
        rootResource.add(linkTo(methodOn(ExampleRootRestController.class).greeting("geek")).withRel("greeting"));
        rootResource.add(linkTo(methodOn(ExampleRootRestController.class).greetingWithJavaconfig("geek")).withRel("greetingWithJavaconfig"));

        rootResource.add(linkTo(methodOn(ExampleUserRestController.class).getAllUsers()).withRel("users"));
        rootResource.add(linkTo(methodOn(ExampleUserRestController.class).getAllUsers1()).withRel("users var 1"));
        rootResource.add(linkTo(methodOn(ExampleUserRestController.class).getAllUsers2()).withRel("users var 2"));
        rootResource.add(linkTo(methodOn(ExampleUserRestController.class).getAllUsers3()).withRel("users var 3"));

        rootResource.add(linkTo(methodOn(ExampleUserRestController.class).getUserById(new ObjectId("5c3a0dab3c56f736c010669c"))).withRel("getUserById(\"5c3a0dab3c56f736c010669c\")"));

        rootResource.add(linkTo(methodOn(ExampleUserRestController.class).getUserByName("test")).withRel("getUserByName(\"test\")"));
        rootResource.add(linkTo(methodOn(ExampleUserRestController.class).getUserByName("ivan")).withRel("getUserByName(\"ivan\")"));

        rootResource.add(linkTo(methodOn(ExampleRegisterRestController.class).getUserByName("test")).withRel("ExampleRegisterRestController.getUserByName(\"test\") whit @CrossOrigin"));
        return rootResource;
    }

    //    //https://spring.io/guides/gs/rest-service-cors/
    @CrossOrigin(origins = "http://localhost:9000")
    @GetMapping("/greeting")
    public GreetingResponse greeting(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting ====");

        //send message Websocket
        this.simpMessagingTemplate.convertAndSend("/topic/greetings", String.format(template, name));

        //send rest JSON
        return new GreetingResponse(counter.incrementAndGet(), String.format(template, name));
    }

    @CrossOrigin
    @GetMapping("/greeting2")
    public GreetingResponse greetingWithJavaconfig(@RequestParam(required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting2 ====");
        return new GreetingResponse(counter.incrementAndGet(), String.format(template, name));
    }

}
