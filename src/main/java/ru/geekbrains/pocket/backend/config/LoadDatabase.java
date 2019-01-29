package ru.geekbrains.pocket.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.pocket.backend.domain.db.Role;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.repository.RoleRepository;
import ru.geekbrains.pocket.backend.repository.UserRepository;

import java.util.Arrays;

@Configuration
@Slf4j
class LoadDatabase {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired
//    SecurityConfig securityConfig;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        return args -> {

            addRoleToDB(new Role("ROLE_ADMIN"));
            addRoleToDB(new Role("ROLE_USER"));

            //userRepository.deleteAll();
            //userRepository.deleteByEmail("a@mail.ru");

            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN");
            Role roleUser = roleRepository.findByName("ROLE_USER");


            //addUserToDB(new User("a@mail.ru", securityConfig.passwordEncoder().encode("Abc123"), "Alex",
            addUserToDB(new User("a@mail.ru", passwordEncoder.encode("Abc123"), "Alex",
                    Arrays.asList(roleAdmin, roleUser)));
            addUserToDB(new User("b@mail.ru", passwordEncoder.encode("Abc345"), "Bob",
                    Arrays.asList(roleUser)));
            addUserToDB(new User("i@mail.ru", passwordEncoder.encode("Abc567"), "ivan",
                    Arrays.asList(roleUser)));
        };
    }

    private void addRoleToDB(Role role) {
        if (!roleRepository.exists(Example.of(role)))
            log.info("Preloading " + roleRepository.save(role));
    }

    private void addUserToDB(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null)
            log.info("Preloading " + userRepository.save(user));
    }
}
