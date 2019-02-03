package ru.geekbrains.pocket.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.pocket.backend.domain.db.*;
import ru.geekbrains.pocket.backend.repository.PrivilegeRepository;
import ru.geekbrains.pocket.backend.repository.RoleRepository;
import ru.geekbrains.pocket.backend.repository.UserRepository;
import ru.geekbrains.pocket.backend.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private boolean alreadySetup = false;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        //userRepository.deleteAll();
        //userRepository.deleteByEmail("a@mail.ru");

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        final Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        final Privilege passwordPrivilege = createPrivilegeIfNotFound("CHANGE_PASSWORD_PRIVILEGE");

        // == create initial roles
        final List<Privilege> adminPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege));
        final List<Privilege> userPrivileges = new ArrayList<Privilege>(Arrays.asList(readPrivilege, passwordPrivilege));
        final Role adminRole = createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        final Role userRole = createRoleIfNotFound("ROLE_USER", userPrivileges);

        // == create initial user
        User user1 = createUserIfNotFound("test@test.com", "Test", "Test1234", Arrays.asList(adminRole));
        User user2 = createUserIfNotFound("a@mail.ru", "Alex", "Abc12345", Arrays.asList(adminRole));
        User user3 = createUserIfNotFound("b@mail.ru", "Bob", "Abc12345", Arrays.asList(userRole));
        User user4 = createUserIfNotFound("i@mail.ru", "ivan", "Qwe12345", Arrays.asList(userRole));

        createTokenForUser(user1);
        createTokenForUser(user2);
        createTokenForUser(user3);
        //createTokenForUser(user4); не создаём токен специально для тестирования

        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilege = privilegeRepository.save(privilege);
            log.info("Preloading " + privilege);
        }
        return privilege;
    }

    @Transactional
    private Role createRoleIfNotFound(final String name, final List<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
        }
        role.setPrivileges(privileges);
        role = roleRepository.save(role);
        log.info("Preloading " + role);
        return role;
    }

    @Transactional
    private User createUserIfNotFound(final String email, final String userName, final String password, final Collection<Role> roles) {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setUsername(userName);
            user.setPassword(passwordEncoder.encode(password));
            user.setProfile(new UserProfile(userName));
            user.setEnabled(true);
        }
        user.setRoles(roles);
        user = userService.update(user);
        log.info("Preloading " + user);
        return user;
    }

    @Transactional
    private UserToken createTokenForUser(User user) {
        UserToken token = userService.getVerificationToken(user);
        if (token == null) {
            token = userService.createVerificationTokenForUser(user);
        }
        return token;
    }
//    @Bean
//    CommandLineRunner initDatabase() {
//        return args -> {
//            log.info("initDatabase");
//        };
//    }

}
