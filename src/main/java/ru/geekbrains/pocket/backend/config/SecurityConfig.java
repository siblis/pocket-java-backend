package ru.geekbrains.pocket.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import ru.geekbrains.pocket.backend.security.*;
import ru.geekbrains.pocket.backend.security.google2fa.CustomWebAuthenticationDetailsSource;

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
    @Autowired
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    public SecurityConfig() {
        super();
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
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
                    .antMatchers("/login*", "/logout*", "/signin/**", "/signup/**", "/customLogin*",
                            "/registration*", "/registrationConfirm*",
                            "/expiredAccount*", "/badUser*", "/forgetPassword*", "/user/resetPassword*",
                            "/user/changePassword*", "/emailError*", "/successRegister*","/qrcode*").permitAll()
    //                .antMatchers("/login*", "/logout*",
    //                        "/auth/resendRegistrationToken*").permitAll()
                    .antMatchers("/v1/auth/**").permitAll() //rest
                    .antMatchers("/auth/**").permitAll() //web
                    .antMatchers("/resources/**", "/webjars/**", "/static/**").permitAll() //web
                    .antMatchers("/test/**").permitAll() //websocket
                    .antMatchers("/invalidSession*").anonymous()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/web/**", "/v1/**").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/user/updatePassword*","/user/savePassword*","/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                    .anyRequest().hasAuthority("READ_PRIVILEGE")
                    //.anyRequest().authenticated()
                .and()
                .formLogin()
                    //.passwordParameter("")
                    .defaultSuccessUrl("/homepage.html")
                    .loginPage("/login")
                    //.loginProcessingUrl("/authenticateTheUser")
                    .failureUrl("/login?error=true")
                    .successHandler(customAuthenticationSuccessHandler)
                    .failureHandler(customAuthenticationFailureHandler)
                    .authenticationDetailsSource(authenticationDetailsSource)
                    .permitAll()
                .and()
                    .sessionManagement()
                    .invalidSessionUrl("/invalidSession.html")
                    .maximumSessions(1).sessionRegistry(sessionRegistry()).and()
                    .sessionFixation().none()
                .and()
                .logout()
                    //.logoutUrl("/logout")
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .invalidateHttpSession(false)
                    .logoutSuccessUrl("/logout.html?logSucc=true")
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                    .httpBasic()
                .and()
                .rememberMe().rememberMeServices(rememberMeServices()).key("theKey")
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
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        CustomRememberMeServices rememberMeServices = new CustomRememberMeServices("theKey", userDetailsService, new InMemoryTokenRepositoryImpl());
        return rememberMeServices;
    }

//    @Bean(name = "restTokenAuthenticationFilter")
//    public TokenAuthenticationFilter restTokenAuthenticationFilter() {
//        TokenAuthenticationFilter restTokenAuthenticationFilter = new TokenAuthenticationFilter();
//        tokenAuthenticationManager.setUserDetailsService(userService);
//        restTokenAuthenticationFilter.setAuthenticationManager(tokenAuthenticationManager);
//        return restTokenAuthenticationFilter;
//    }

}