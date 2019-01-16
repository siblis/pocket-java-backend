package ru.geekbrains.pocket.backend.config;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableCaching
@EnableMongoRepositories(basePackages = {"ru.geekbrains.pocket.backend.repository"})
public class AppConfig extends WebMvcAutoConfiguration {

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext context) {

        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

        return mongoTemplate;

    }

    //https://spring.io/guides/gs/rest-service-cors/
    //https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-cors
    //надо прописать для любых ответов: и 200+ и ошибок и редиректов, если будут и даже для 500.
    //res.setHeader('Access-Control-Allow-Origin', '*'); - разрешает передачу данных на любой домен (включая тот же домен, но другой порт)
    //res.setHeader('Access-Control-Allow-Methods','OPTIONS, GET, POST, PUT, PATCH, DELETE, UNLINK, LINK'); - наименование разрешенных типов http-запросов
    //res.setHeader('Access-Control-Allow-Headers', 'Authentication'); - список разрешенных заголовков (только дополнительных, стандартные разрешены по умолчанию)
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins("http://localhost:9000")
                        .allowedMethods("*")
                        .allowedHeaders("*")
//                        .exposedHeaders("*")
                        .allowCredentials(true).maxAge(3600);
//                registry.addMapping("/api/users/**")
//                        .allowedOrigins("http://localhost:9000")
//                        .allowedMethods("PUT", "DELETE")
//                        .allowedHeaders("header1", "header2", "header3")
//                        .exposedHeaders("header1", "header2")
            }
        };
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        System.out.println("Filchain");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true); // you USUALLY want this
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.addAllowedMethod("*");
//        source.registerCorsConfiguration("/api/**", config);
//        System.out.println("Filchain");
//        return new CorsFilter(source);
//    }

    public void addResourceHandler(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    //http://spring-projects.ru/guides/caching/
    //https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

}
