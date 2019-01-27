package ru.geekbrains.pocket.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.geekbrains.pocket.backend.repository.UserRepository;
import ru.geekbrains.pocket.backend.service.UserService;

import java.util.Optional;

//@Configuration
class WebSecurityConfig extends GlobalAuthenticationConfigurerAdapter {

//    @Autowired
//    UserRepository userRepository;

//    @Autowired
//    private UserService userService;
//
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService); //userDetailsService());
//    }

//    @Bean
//    UserDetailsService userDetailsService() {
//        return email -> Optional.of(userRepository.findByEmail(email))
//                .map(u -> new User(u.getEmail(), u.getPassword(), true, true, true, true,
//                        AuthorityUtils.createAuthorityList("USER", "write")))
//                .orElseThrow(
//                        () -> new UsernameNotFoundException("could not find the user '"
//                                + email + "'"));
//    }
}
