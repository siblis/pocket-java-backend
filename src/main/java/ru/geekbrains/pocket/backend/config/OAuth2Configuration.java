package ru.geekbrains.pocket.backend.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

//Авторизация и аутентификация клиента в Open Web с использование Spring Security OAuth
//http://spring-projects.ru/guides/tutorials-bookmarks/
//
//curl -X POST -vu android-pocket-java-backend:123456 http://localhost:8189/oauth/token -H "Accept: application/json" -d "password=$2a$10$bKCNdrPJxNVbHK.Z/Yate.6jpHLH8MtWzPG9jD0rR8cNhdDu1a4sO&username=test&grant_type=password&scope=write&client_secret=123456&client_id=pocket-java-backend"
// OR
//curl -X POST -vu android-pocket-java-backend:123456 https://localhost:8189/oauth/token -H "Accept: application/json" -d "password=$2a$10$bKCNdrPJxNVbHK.Z/Yate.6jpHLH8MtWzPG9jD0rR8cNhdDu1a4sO&username=test&grant_type=password&scope=write&client_secret=123456&client_id=pocket-java-backend"
// curl -v POST http://127.0.0.1:8189/tags --data "tags=cows,dogs"  -H "Authorization: Bearer 66953496-fc5b-44d0-9210-b0521863ffcb"

//@Configuration
//@EnableResourceServer
//@EnableAuthorizationServer
class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    private final String applicationName = "pocket-java-backend";

    // This is required for password grants, which we specify below as one of the
    // {@literal authorizedGrantTypes()}.
    //@Autowired
    AuthenticationManagerBuilder authenticationManager;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        // Workaround for https://github.com/spring-projects/spring-boot/issues/1801
        endpoints.authenticationManager(
                authentication -> authenticationManager.getOrBuild().authenticate(authentication));
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("android-" + applicationName)
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .authorities("ROLE_USER").scopes("write").resourceIds(applicationName)
                .secret("123456");
    }
}
