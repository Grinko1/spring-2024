package org.spring.reactive.config;

import org.spring.reactive.client.WebClientProductsClient;
import org.spring.reactive.client.WebFavoriteProductsClient;
import org.spring.reactive.client.WebProductReviewsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration

public class ClientConfig {

    @Bean
    @Scope("prototype")
    public WebClient.Builder selmagServicesWebClientBuilder(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository serverOAuth2AuthorizedClientRepository
    ){
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrationRepository,
                        serverOAuth2AuthorizedClientRepository
                );
        filter.setDefaultClientRegistrationId("keycloak");
        return WebClient.builder()
                .filter(filter);
    }
    @Bean
    public WebClientProductsClient webClientProductsClient(@Value("${catalog.rest.uri:http://localhost:8080}") String baseUrl,
                                                           WebClient.Builder selmagServicesWebClientBuilder ){
        return new WebClientProductsClient(selmagServicesWebClientBuilder
                .baseUrl(baseUrl)
                .build());
    }
    @Bean
    public WebFavoriteProductsClient webClientFavoriteProductsClient(@Value("${catalog.rest.favorite.uri:http://localhost:8084}") String baseUrl,
                                                                     WebClient.Builder selmagServicesWebClientBuilder){
        return new WebFavoriteProductsClient(selmagServicesWebClientBuilder
                .baseUrl(baseUrl)
                .build());
    }
    @Bean
    public WebProductReviewsClient webClientReviewProductsClient(@Value("${catalog.rest.review.uri:http://localhost:8084}") String baseUrl,
                                                                 WebClient.Builder selmagServicesWebClientBuilder){
        return new WebProductReviewsClient(selmagServicesWebClientBuilder
                .baseUrl(baseUrl)
                .build());
    }
}
