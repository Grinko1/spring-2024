package org.spring.reactive.contoller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.spring.reactive.client.FavoriteProductsClient;
import org.spring.reactive.client.ProductReviewsClient;
import org.spring.reactive.client.ProductsClient;
import org.spring.reactive.entity.Product;
import org.spring.reactive.exceptions.ClientBadRequestException;
import org.spring.reactive.payload.NewProductReviewPayload;

import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/customer/products/{productId:\\d+}")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductsClient productsClient;
    private final FavoriteProductsClient favoriteClient;
    private final ProductReviewsClient reviewsClient;

    @ModelAttribute(name = "product", binding = false)
    public Mono<Product> getProduct(@PathVariable("productId") int productId) {
        return productsClient.findProduct(productId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("customer.products.error.not_found")));
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") int productId, Model model) {
        model.addAttribute("inFavorite", false);
        return reviewsClient.findProductReviews(productId)
                .collectList()
                .doOnNext(reviews -> model.addAttribute("reviews", reviews))
                .then(
                        favoriteClient.findFavoriteProductByProductId(productId)
                                .doOnNext(favoriteProduct -> model.addAttribute("inFavorite", true))
                                .thenReturn("customer/products/product")
                );

    }

    @PostMapping("/add-to-favorites")
    public Mono<String> addProductToFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favoriteClient.addProductToFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId))
                        .onErrorResume(exception -> {
                            log.error(exception.getMessage(), exception);
                            return Mono.just("redirect:/customer/products/%d".formatted(productId));
                        }));
    }

    @PostMapping("/remove-from-favorites")
    public Mono<String> deleteProductFromFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favoriteClient.removeProductFromFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId))

                );
    }

    @PostMapping("/create-review")
    public Mono<String> createReview(@PathVariable("productId") int productId,
                                     NewProductReviewPayload payload,
                                     Model model) {

        return reviewsClient.createProductReview(productId, payload.rating(), payload.review())
                .thenReturn("redirect:/customer/products/%d".formatted(productId))
                .onErrorResume(ClientBadRequestException.class,exception ->{
                    model.addAttribute("inFavorite", false);
                    model.addAttribute("payload" , payload);
                    model.addAttribute("errors", exception.getErrors());
                    return favoriteClient.findFavoriteProductByProductId(productId)
                            .doOnNext(favoriteProduct -> model.addAttribute("inFavorite", true))
                            .thenReturn("customer/products/product");
                });
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementExceptionHandler(NoSuchElementException e, Model model){
    model.addAttribute("error" , e.getMessage());
    return "errors/404";
    }
    @ModelAttribute
    public Mono<CsrfToken> loadCsrfToken(ServerWebExchange exchange){
          return exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName()).doOnSuccess(token -> exchange.getAttributes()
                  .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME,
                          token));
    }
}
