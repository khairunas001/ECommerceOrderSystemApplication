package e_commerce_order_system.e_commerce_order_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import e_commerce_order_system.e_commerce_order_system.model.request.request_product.CreateProductRequest;
import e_commerce_order_system.e_commerce_order_system.model.response.response_product.ProductResponse;
import e_commerce_order_system.e_commerce_order_system.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void createProduct_success() throws Exception {
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Laptop");
        request.setPrice(10_000_000L);
        request.setStock(10);

        ProductResponse response = ProductResponse.builder()
                .id("0001")
                .name("Laptop")
                .price(10_000_000L)
                .stock(10)
                .build();

        when(productService.createProduct(any())).thenReturn(response);

        mockMvc.perform(post("/e-commerce/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value("0001"))
                .andExpect(jsonPath("$.data.name").value("Laptop"));
    }

    @Test
    void getProduct_success() throws Exception {
        ProductResponse response = ProductResponse.builder()
                .id("0001")
                .name("Laptop")
                .price(10_000_000L)
                .stock(10)
                .build();

        when(productService.getProduct("0001")).thenReturn(response);

        mockMvc.perform(get("/e-commerce/products/{product_id}", "0001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("0001"))
                .andExpect(jsonPath("$.data.name").value("Laptop"));
    }

    @Test
    void getAllProduct_success() throws Exception {
        List<ProductResponse> responses = List.of(
                ProductResponse.builder()
                        .id("0001")
                        .name("Laptop")
                        .price(10_000_000L)
                        .stock(10)
                        .build()
        );

        when(productService.getAllProduct()).thenReturn(responses);

        mockMvc.perform(get("/e-commerce/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value("0001"))
                .andExpect(jsonPath("$.data[0].name").value("Laptop"));
    }

    @Test
    void deleteProduct_success() throws Exception {
        mockMvc.perform(delete("/e-commerce/products/{product_id}", "0001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("data already deleted"));
    }
}
