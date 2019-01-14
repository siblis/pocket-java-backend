package ru.geekbrains.pocket.backend.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import ru.geekbrains.pocket.backend.domain.Security.User;
import ru.geekbrains.pocket.backend.repository.security.UserRepository;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            User user = new User("Alex", "123", "a@mail.ru");
            Example<User> exampleQuery = Example.of(user);
            if (!repository.exists(exampleQuery))
                log.info("Preloading " + repository.save(user));

            user = new User("Bob", "1234", "b@mail.ru");
            if (!repository.exists(Example.of(user)))
                log.info("Preloading " + repository.save(user));

            user = new User("ivan", "1235", "i@mail.ru");
            if (!repository.exists(Example.of(user)))
                log.info("Preloading " + repository.save(user));

        };
    }
}
