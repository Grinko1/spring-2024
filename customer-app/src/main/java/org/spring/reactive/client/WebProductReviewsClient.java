package org.spring.reactive.client;

import lombok.RequiredArgsConstructor;
import org.spring.reactive.entity.ProductReview;
import org.spring.reactive.exceptions.ClientBadRequestException;
import org.spring.reactive.payload.NewProductPayload;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RequiredArgsConstructor
public class WebProductReviewsClient implements ProductReviewsClient {
    private final WebClient webClient;

    @Override
    public Flux<ProductReview> findProductReviews(Integer productId) {
        return webClient
                .get()
                .uri("/feedback-api/product-reviews/productId/{productId}", productId)
                .retrieve()
                .bodyToFlux(ProductReview.class);
    }

    @Override
    public Mono<ProductReview> createProductReview(Integer productId, Integer rating, String review) {
        return webClient
                .post()
                .uri("/feedback-api/product-reviews")
                .bodyValue(new NewProductPayload(productId, rating,review))
                .retrieve()
                .bodyToMono(ProductReview.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                (List<String>) exception.getResponseBodyAs(ProblemDetail.class).getProperties().get("errors")) )
                ;
    }
}
