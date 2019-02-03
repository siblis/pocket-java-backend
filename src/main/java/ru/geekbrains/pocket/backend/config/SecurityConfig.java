package ru.geekbrains.pocket.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.geekbrains.pocket.backend.security.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    public SecurityConfig() {
        super();
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   //Межсайтовая подделка запроса
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(restAuthenticationEntryPoint)
                    .and()
//            .headers().frameOptions().sameOrigin()
//                .and()
//                .addFilterAfter(restTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/v1/auth/**").permitAll() //rest
                    .antMatchers("/test/**").permitAll() //websocket
                    .antMatchers("/invalidSession*").anonymous()
                    .antMatchers("/v1/**").hasAnyRole("ADMIN", "USER")
                    //.anyRequest().hasAuthority("READ_PRIVILEGE")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    //.passwordParameter("")
                    .defaultSuccessUrl("/homepage.html")
                    .loginPage("/login")
                    .successHandler(customAuthenticationSuccessHandler)
                    .failureHandler(customAuthenticationFailureHandler)
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .invalidateHttpSession(false)
                    .deleteCookies("JSESSIONID")
                    .permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

//    @Bean(name = "restTokenAuthenticationFilter")
//    public TokenAuthenticationFilter restTokenAuthenticationFilter() {
//        TokenAuthenticationFilter restTokenAuthenticationFilter = new TokenAuthenticationFilter();
//        tokenAuthenticationManager.setUserDetailsService(userService);
//        restTokenAuthenticationFilter.setAuthenticationManager(tokenAuthenticationManager);
//        return restTokenAuthenticationFilter;
//    }

}