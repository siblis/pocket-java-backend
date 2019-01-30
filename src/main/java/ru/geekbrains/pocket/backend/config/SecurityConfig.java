package ru.geekbrains.pocket.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import ru.geekbrains.pocket.backend.security.CustomAccessDeniedHandler;
import ru.geekbrains.pocket.backend.security.CustomLogoutSuccessHandler;
import ru.geekbrains.pocket.backend.security.MySavedRequestAwareAuthenticationSuccessHandler;
import ru.geekbrains.pocket.backend.security.RestAuthenticationEntryPoint;
import ru.geekbrains.pocket.backend.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@ComponentScan("ru.geekbrains.pocket.backend.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;

    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();

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
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/web/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/register/**").permitAll() //web
                .antMatchers("/webjars/**").permitAll() //web
                .antMatchers("/static/**").permitAll() //web
                .antMatchers("/test/**").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.passwordParameter("")
                .defaultSuccessUrl("/web")
                .loginPage("/login")
                .loginProcessingUrl("/authenticateTheUser")
                .successHandler(mySuccessHandler)
                .failureHandler(myFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                //See https://jira.springsource.org/browse/SPR-11496
                .headers().addHeaderWriter(
                new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
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