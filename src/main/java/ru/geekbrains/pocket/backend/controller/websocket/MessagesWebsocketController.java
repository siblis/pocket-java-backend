package ru.geekbrains.pocket.backend.controller.websocket;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.util.HtmlUtils;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.domain.db.UserMessage;
import ru.geekbrains.pocket.backend.service.UserMessageService;
import ru.geekbrains.pocket.backend.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

//https://o7planning.org/ru/10719/create-a-simple-chat-application-with-spring-boot-and-websocket
//https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket-server

@Slf4j
@Controller
public class MessagesWebsocketController implements ApplicationListener<BrokerAvailabilityEvent> {

    private AtomicBoolean brokerAvailable = new AtomicBoolean(); //доступность брокера

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private UserMessageService userMessageService;
    @Autowired
    private UserService userService;

    public MessagesWebsocketController(SimpMessagingTemplate simpMessagingTemplate,
                                       UserMessageService userMessageService,
                                       UserService userService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userMessageService = userMessageService;
        this.userService = userService;
    }

    @MessageMapping("/send") //Отправить новое сообщение
    @SendToUser("/topic/send") //Событие "Новое сообщение"
    public String clientSendMessage(@Payload ClientSendMessage message) {
//                                    Principal principal,
//                                    @Header("simpSessionId") String sessionId) throws Exception {
        String response = "Error";
        if (message == null || message.getText() == null || message.getText().equals("")) {
            //TODO проверка на ошибки
        } else {
            User sender = userService.getUserByUsername("Alex");//principal.getName());
            if (message.getGroup() != null && !message.getGroup().equals("")) {
                //TODO запись сообщения для группы в бд
                //TODO отправить сообщение группе
                response = "messageId";
            } else if (message.getRecipient() != null && !message.getRecipient().equals("")) {
                User recipient = userService.getUserById(new ObjectId(message.getRecipient()));
                if (recipient != null) {
                    UserMessage userMessage = userMessageService.createMessage(sender, recipient, message.getText());
                    response = userMessage.getId().toString();

                    //TODO отправить сообщение получателю
                    //send message Websocket
                    this.simpMessagingTemplate.convertAndSend("/queue/new", message.getText());
                }
            }
        }
        return response;
    }

    @MessageMapping("/read") //Прочитал сообщение
    @SendToUser("/queue/read") //Cобытие "Пользователь прочитал сообщение"
    public ServerMessageRead clientMessageRead(@Payload ClientMessageRead message, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        //TODO найти сообщение в бд и получить получателя
        String recipient = "";
        System.out.println("test readMessage: " + message.getSender());
        System.out.println("test readMessage: " + HtmlUtils.htmlEscape(message.getSender()));
        return new ServerMessageRead(message.getIdMessage(), recipient);
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        System.out.println("handleException : " + exception.getMessage());
        //System.out.println(exception.toString());
        return exception.getMessage();
    }


    @Override
    public void onApplicationEvent(BrokerAvailabilityEvent event) {
        this.brokerAvailable.set(event.isBrokerAvailable());
    }


    //=========Models request & response=========
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class TestMessage {
        private String username;
        private String email;
        private String text;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClientSendMessage {
        private String text;
        private String group;
        private String recipient;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ClientMessageRead {
        private String idMessage;
        private String sender;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ServerMessageRead {
        private String idMessage;
        private String recipient;
    }
}
