package ru.geekbrains.pocket.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import ru.geekbrains.pocket.backend.domain.Role;
import ru.geekbrains.pocket.backend.domain.User;
import ru.geekbrains.pocket.backend.repository.RoleRepository;
import ru.geekbrains.pocket.backend.repository.UserRepository;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            Role role = new Role("ROLE_ADMIN");
            if (!roleRepository.exists(Example.of(role)))
                log.info("Preloading " + roleRepository.save(role));

            role = new Role("ROLE_USER");
            if (!roleRepository.exists(Example.of(role)))
                log.info("Preloading " + roleRepository.save(role));

            User user = new User("Alex", "123", "a@mail.ru");
            Example<User> exampleQuery = Example.of(user);
            if (!userRepository.exists(exampleQuery))
                log.info("Preloading " + userRepository.save(user));

            user = new User("Bob", "1234", "b@mail.ru");
            if (!userRepository.exists(Example.of(user)))
                log.info("Preloading " + userRepository.save(user));

            user = new User("ivan", "1235", "i@mail.ru");
            if (!userRepository.exists(Example.of(user)))
                log.info("Preloading " + userRepository.save(user));

        };
    }
}
