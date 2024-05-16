package org.spring.reactive.contoller;

import lombok.RequiredArgsConstructor;
import org.spring.reactive.client.ProductsClient;
import org.spring.reactive.entity.Product;
import org.spring.reactive.service.FavoriteProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/customer/products/{productId:\\d+}")
@RequiredArgsConstructor
public class ProductController {
    private final ProductsClient productsClient;
    private final FavoriteProductsService favoriteProductsService;

    @ModelAttribute(name = "product", binding = false)
    public Mono<Product> getProduct(@PathVariable("productId") int productId) {
        return productsClient.findProduct(productId);
    }

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") int productId, Model model) {
        model.addAttribute("inFavorite", false);
        return favoriteProductsService.findFavoriteProductByProductId(productId)
                .doOnNext(favoriteProduct -> model.addAttribute("inFavorite", true) )
                .thenReturn("customer/products/product");
    }

    @PostMapping("/add-to-favorites")
    public Mono<String> addProductToFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favoriteProductsService.addProductToFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId))
                ) ;
    }
    @PostMapping("/remove-from-favorites")
    public Mono<String> deleteProductFromFavorites(@ModelAttribute("product") Mono<Product> productMono) {
        return productMono
                .map(Product::id)
                .flatMap(productId -> favoriteProductsService.removeProductFromFavorites(productId)
                        .thenReturn("redirect:/customer/products/%d".formatted(productId))
                ) ;
    }
}
