package ru.geekbrains.pocket.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import static org.springframework.messaging.simp.SimpMessageType.*;

//https://www.baeldung.com/spring-security-websockets

@Configuration
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        messages
                .simpTypeMatchers(CONNECT, UNSUBSCRIBE, DISCONNECT).permitAll()
                .simpDestMatchers("/secured/**", "/secured/**/**").authenticated()
                .simpDestMatchers("/v1/**", "/ws", "/ws/**").permitAll()
                .simpDestMatchers("/topic", "/topic/**").permitAll()
                .simpDestMatchers("/queue", "/queue/**").permitAll()
                .simpDestMatchers("/socket", "/socket/**").permitAll()
                //.simpDestMatchers("/topic/**").authenticated()
                //.simpDestMatchers("/app/**").hasRole("ADMIN")
                //.simpSubscribeDestMatchers("/topic/**").authenticated()
                .anyMessage().authenticated();

// message types other than MESSAGE and SUBSCRIBE
//            .nullDestMatcher().authenticated()
//      .simpTypeMatchers(SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).denyAll()
//      catch all
//            .anyMessage().denyAll();

    }

//    @Override
//    public void customizeClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new MyChannelInterceptor()); //перехват всех сообщений
//    }

}
