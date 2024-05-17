package org.spring.reactive.contoller;


import lombok.RequiredArgsConstructor;
import org.spring.reactive.client.FavoriteProductsClient;
import org.spring.reactive.client.ProductsClient;
import org.spring.reactive.entity.FavoriteProduct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer/products")
public class ProductsController {

    private final ProductsClient productsClient;
    private final FavoriteProductsClient favoritesService;

    @GetMapping("/list")
    public Mono<String> getAllProducts(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return productsClient.findAllProducts(filter).collectList()
                .doOnNext(products ->
                        model.addAttribute("products", products))
                .thenReturn("customer/products/list")
                ;
    }

    @GetMapping("/favorites")
    public Mono<String> getAllFavoritesProducts(Model model, @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);

        return favoritesService.findFavoriteProducts()
                .map(FavoriteProduct::productId)
                .collectList()
                .flatMap(favoriteProducts -> productsClient.findAllProducts(filter)
                        .filter(product -> favoriteProducts.contains(product.id()))
                        .collectList()
                        .doOnNext(products -> model.addAttribute("products", products)))
                .thenReturn("customer/products/favorites");


    }
}
