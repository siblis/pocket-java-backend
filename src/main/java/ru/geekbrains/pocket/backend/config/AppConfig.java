package ru.geekbrains.pocket.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
//@EnableCaching
@EnableMongoRepositories(basePackages = {"ru.geekbrains.pocket.backend.repository"})
public class AppConfig {

//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        return new MethodValidationPostProcessor();
//    }

//    @Bean
//    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
//        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
//        validatorFactoryBean.setValidationMessageSource(messageSource);
//        return validatorFactoryBean;
//    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory, MongoMappingContext context) {

        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory, converter);

        return mongoTemplate;

    }

    //http://spring-projects.ru/guides/caching/
    //https://docs.spring.io/spring/docs/current/spring-framework-reference/integration.html#cache
//    @Bean
//    public CacheManager cacheManager() {
//        return new ConcurrentMapCacheManager();
//    }

}
