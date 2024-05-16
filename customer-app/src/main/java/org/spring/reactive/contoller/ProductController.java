package org.spring.reactive.contoller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.reactive.client.ProductsClient;
import org.spring.reactive.entity.Product;
import org.spring.reactive.payload.NewProductReviewPayload;
import org.spring.reactive.service.FavoriteProductsService;
import org.spring.reactive.service.ProductReviewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/customer/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {
    private final ProductsClient productsClient;
    private final FavoriteProductsService favoriteProductsService;
    private final ProductReviewsService reviewsService;

    @ModelAttribute(name = "product", binding = false)
    public Mono<Product> getProduct(@PathVariable("productId") int productId) {
        return productsClient.findProduct(productId)
                .switchIfEmpty(Mono.error(new NoSuchElementException("customer.products.error.not_found")));
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") int productId, Model model) {
        model.addAttribute("inFavorite", false);
        return reviewsService.findProductReviewsByProductId(productId)
                .collectList()
                .doOnNext(reviews -> model.addAttribute("reviews", reviews))
                .then(
                        favoriteProductsService.findFavoriteProductByProductId(productId)
                                .doOnNext(favoriteProduct -> model.addAttribute("inFavorite", true))
                                .thenReturn("customer/products/product")
                );

    }

    @PostMapping("/add-to-favorites")
    public Mono<String> addProductToFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favoriteProductsService.addProductToFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId))
                );
    }

    @PostMapping("/remove-from-favorites")
    public Mono<String> deleteProductFromFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favoriteProductsService.removeProductFromFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId))
                );
    }

    @PostMapping("/create-review")
    public Mono<String> createReview(@PathVariable("productId") int productId,
                                     @Valid NewProductReviewPayload payload,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("inFavorite", false);
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult
                    .getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList());
            return Mono.just("/customer/products/product");
        }
        return reviewsService.createProductReview(productId, payload.rating(), payload.review())
                .thenReturn("redirect:/customer/products/%d".formatted(productId));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementExceptionHandler(NoSuchElementException e, Model model){
    model.addAttribute("error" , e.getMessage());
    return "errors/404";
    }
}
