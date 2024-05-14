package org.spring.mvc.config;

import org.spring.mvc.client.RestClientProductRestClient;
import org.spring.mvc.security.OAuthClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {
    @Bean
    public RestClientProductRestClient productRestClient(@Value("${catalog.service.uri:http://localhost:8080}") String catalogUri,
                                                         ClientRegistrationRepository clientRegistrationRepository,
                                                         OAuth2AuthorizedClientRepository authorizedClientRepository,
                                                         @Value("${catalog.services.catalog.registration-id:keycloak}") String registrationId
    ) {
        return new RestClientProductRestClient(RestClient.builder()
                .baseUrl(catalogUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        authorizedClientRepository), registrationId))
                .build());
    }
}
