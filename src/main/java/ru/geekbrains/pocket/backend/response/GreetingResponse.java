package ru.geekbrains.pocket.backend.response;

import lombok.Getter;

//https://spring.io/guides/gs/rest-service-cors/

@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class GreetingResponse {
    private final long id;
    private final String content;

    public GreetingResponse() {
        this.id = -1;
        this.content = "";
    }

    public GreetingResponse(String content) {
        this.id = -1;
        this.content = content;
    }

    public GreetingResponse(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
