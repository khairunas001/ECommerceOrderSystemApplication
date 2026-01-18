package e_commerce_order_system.e_commerce_order_system.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import e_commerce_order_system.e_commerce_order_system.model.request.request_product.CreateProductRequest;
import e_commerce_order_system.e_commerce_order_system.model.request.request_product.UpdateProductRequest;
import e_commerce_order_system.e_commerce_order_system.model.response.ResponseFactory;
import e_commerce_order_system.e_commerce_order_system.model.response.response_product.ProductResponse;
import e_commerce_order_system.e_commerce_order_system.model.web_response.WebResponseSuccess;
import e_commerce_order_system.e_commerce_order_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(
            path = "/e-commerce/products",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<WebResponseSuccess<ProductResponse>> createProduct(@RequestBody CreateProductRequest request) {

        ProductResponse response = productService.createProduct(request);

        return ResponseFactory.created(response);
    }

    @GetMapping(path = "/e-commerce/products/{product_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponseSuccess<ProductResponse>> getProduct(@PathVariable(name = "product_id") String productId) {

        ProductResponse response = productService.getProduct(productId);

        return ResponseFactory.ok(response);
    }

    @GetMapping(path = "/e-commerce/products",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponseSuccess<List<ProductResponse>>> getAllProduct() throws JsonProcessingException {

        List<ProductResponse> response = productService.getAllProduct();

        return ResponseFactory.ok(response);
    }


    @DeleteMapping(path = "/e-commerce/products/{product_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponseSuccess<String>> deleteProduct(@PathVariable(name = "product_id") String productId) {

        productService.deleteProduct(productId);

        return ResponseFactory.ok("data already deleted");
    }

    @PatchMapping(path = "/e-commerce/products/{product_id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponseSuccess<ProductResponse>> updateProduct(@PathVariable(name = "product_id") String productId, @RequestBody UpdateProductRequest request) {

        ProductResponse response = productService.updateProduct(productId, request);

        return ResponseFactory.ok(response);
    }
}
