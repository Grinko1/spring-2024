package org.spring.mvc.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.mvc.client.ProductsRestClient;
import org.spring.mvc.entity.Product;
import org.spring.mvc.payload.NewProductPayload;
import org.springframework.ui.ConcurrentModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {
    @Mock
    ProductsRestClient productsRestClient;
    @InjectMocks
    ProductsController controller;


    @Test
    @DisplayName("createProduct will create new product and return redirect to product details page")
    void createProduct_RequestIsValid_ReturnsRedirectionToProductPage() {
        //given
        var payload = new NewProductPayload("Test title", "test details");
        var model = new ConcurrentModel();
        doReturn(new Product(1,"Test title", "test details" ))
                .when(productsRestClient)
                .createProduct("Test title", "test details");

        //when
        var result = this.controller.createProduct(payload, model);
        //then
        assertEquals("redirect:/catalog/products/1",result);
        verify(this.productsRestClient).createProduct("Test title", "test details");
        verifyNoInteractions(this.productsRestClient);

    }
}