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
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        return args -> {
            addRoleToDB(new Role("ROLE_ADMIN"));
            addRoleToDB(new Role("ROLE_USER"));

            addUserToDB(new User("a@mail.ru", "Alex", "123"));
            addUserToDB(new User("b@mail.ru", "Bob", "1234"));
            addUserToDB(new User("i@mail.ru", "ivan", "1235"));

//            addUserToDB(new User("a@mail.ru","Alex","123",
//                    new ArrayList<Role>(Arrays.asList(new Role("ROLE_ADMIN"),new Role("ROLE_USER")))));
//            addUserToDB(new User("b@mail.ru","Bob", "1234",
//                    new ArrayList<Role>(Arrays.asList(new Role("ROLE_USER")))));
//            addUserToDB(new User("i@mail.ru","ivan", "1235",
//                    new ArrayList<Role>(Arrays.asList(new Role("ROLE_USER")))));
        };
    }

    private void addRoleToDB(Role role) {
        if (!roleRepository.exists(Example.of(role)))
            log.info("Preloading " + roleRepository.save(role));
    }

    private void addUserToDB(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null)
            log.info("Preloading " + userRepository.save(user));

//        if (!userRepository.exists(Example.of(user)))
//            log.info("Preloading " + userRepository.save(user));
    }
}
