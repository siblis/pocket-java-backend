package ru.geekbrains.pocket.backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EntityScan(basePackages = {"ru.geekbrains.pocket.backend.domain"})
@EnableJpaRepositories(basePackages = {"ru.geekbrains.pocket.backend.repository"})
public class AppConfig extends WebMvcAutoConfiguration {

    public void addResourceHandler(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

}
