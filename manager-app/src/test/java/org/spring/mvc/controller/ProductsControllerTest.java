package org.spring.mvc.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.mvc.client.BadRequestException;
import org.spring.mvc.client.ProductsRestClient;
import org.spring.mvc.entity.Product;
import org.spring.mvc.payload.NewProductPayload;
import org.springframework.ui.ConcurrentModel;

import java.util.List;

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
                .when(this.productsRestClient)
                .createProduct("Test title", "test details");

        //when
        var result = this.controller.createProduct(payload, model);
        //then
        assertEquals("redirect:/catalog/products/1",result);
        verify(this.productsRestClient).createProduct("Test title", "test details");
        verifyNoInteractions(this.productsRestClient);

    }
    @Test
    @DisplayName("createProduct will return the same page with errors")
    void createProduct_RequestIsInValid_ReturnsCreateProductPageWithErrors() {

        //given
        var payload = new NewProductPayload("  ", null);
        var model = new ConcurrentModel();
          doThrow(new BadRequestException(List.of("Error1", "Error2")))
                  .when(this.productsRestClient)
                  .createProduct("  ", null);
        //when
        var result = this.controller.createProduct(payload, model);
        //then
        assertEquals("catalog/products/new_product", result);
        assertEquals(payload, model.getAttribute("payload"));
        assertEquals(List.of("Error1", "Error2"), model.getAttribute("errors"));
        verify(this.productsRestClient).createProduct("  ", null);
        verifyNoInteractions(this.productsRestClient);



    }

}