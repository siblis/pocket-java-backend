package ru.geekbrains.pocket.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.WebJarsResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.Locale;

@Configuration
@EnableWebMvc
//@EnableAsync
public class WebConfig implements WebMvcConfigurer {

    //===CORS===

    //https://spring.io/guides/gs/rest-service-cors/
    //https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-cors
    //надо прописать для любых ответов: и 200+ и ошибок и редиректов, если будут и даже для 500.
    //res.setHeader('Access-Control-Allow-Origin', '*'); - разрешает передачу данных на любой домен (включая тот же домен, но другой порт)
    //res.setHeader('Access-Control-Allow-Methods','OPTIONS, GET, POST, PUT, PATCH, DELETE, UNLINK, LINK'); - наименование разрешенных типов http-запросов
    //res.setHeader('Access-Control-Allow-Headers', 'Authentication'); - список разрешенных заголовков (только дополнительных, стандартные разрешены по умолчанию)
/*    @Bean
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
//                registry.addMapping("/web/**")
//                        .allowedOrigins("http://localhost:9000")
//                        .allowedMethods("*")
//                        .allowedHeaders("*");
//                registry.addMapping("/test/**")
//                        .allowedOrigins("http://localhost:9000")
//                        .allowedMethods("*")
//                        .allowedHeaders("*");
//                registry.addMapping("/ws/**")
//                        .allowedOrigins("http://localhost:9000")
//                        .allowedMethods("*")
//                        .allowedHeaders("*");

//                registry.addMapping("/api/users/**")
//                        .allowedOrigins("http://localhost:9000")
//                        .allowedMethods("PUT", "DELETE")
//                        .allowedHeaders("header1", "header2", "header3")
//                        .exposedHeaders("header1", "header2")
            }
        };
    }*/

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

    //===WEB===
    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/view/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }

    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {
            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("/webjars/")
                    .resourceChain(false)
                    .addResolver(new WebJarsResourceResolver())
                    .addResolver(new PathResourceResolver());
            //registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        }
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }

    //Межсайтовая подделка запроса
    //https://ru.wikipedia.org/wiki/%D0%9C%D0%B5%D0%B6%D1%81%D0%B0%D0%B9%D1%82%D0%BE%D0%B2%D0%B0%D1%8F_%D0%BF%D0%BE%D0%B4%D0%B4%D0%B5%D0%BB%D0%BA%D0%B0_%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81%D0%B0
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("/badUser.html");
        registry.addViewController("/csrfAttacker.html");
        registry.addViewController("/invalidSession.html");
    }

//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }

//    @Override
//    public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
//        converters.add(new MappingJackson2HttpMessageConverter());
//    }

}
