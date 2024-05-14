package org.spring.mvc.config;

import org.spring.mvc.client.RestClientProductRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public RestClientProductRestClient productRestClient(@Value("${catalog.service.uri:http://localhost:8080}") String catalogUri,
                                                         @Value("${catalog.service.username:}") String username,
                                                         @Value("${catalog.service.password:}") String password){
        System.out.println("username "+ username + " password " + password);
        return new RestClientProductRestClient(RestClient.builder()
                .baseUrl(catalogUri)
                .requestInterceptor(new BasicAuthenticationInterceptor(username, password))
                .build());
    }
}
