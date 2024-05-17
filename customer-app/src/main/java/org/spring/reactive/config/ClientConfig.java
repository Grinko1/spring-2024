package org.spring.reactive.config;

import org.spring.reactive.client.WebClientProductsClient;
import org.spring.reactive.client.WebFavoriteProductsClient;
import org.spring.reactive.client.WebProductReviewsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration

public class ClientConfig {
    @Bean
    public WebClientProductsClient webClientProductsClient(@Value("${catalog.rest.uri:http://localhost:8080}") String baseUrl){
        return new WebClientProductsClient(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }
    @Bean
    public WebFavoriteProductsClient webClientFavoriteProductsClient(@Value("${catalog.rest.favorite.uri:http://localhost:8084}") String baseUrl){
        return new WebFavoriteProductsClient(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }
    @Bean
    public WebProductReviewsClient webClientReviewProductsClient(@Value("${catalog.rest.review.uri:http://localhost:8084}") String baseUrl){
        return new WebProductReviewsClient(WebClient.builder()
                .baseUrl(baseUrl)
                .build());
    }
}
