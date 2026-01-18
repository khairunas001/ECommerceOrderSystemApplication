package e_commerce_order_system.e_commerce_order_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import e_commerce_order_system.e_commerce_order_system.model.response.response_product.ProductResponse;
import e_commerce_order_system.e_commerce_order_system.model.response.response_transaction.TransactionResponse;
import e_commerce_order_system.e_commerce_order_system.model.request.request_transaction.CreateTransactionRequest;
import e_commerce_order_system.e_commerce_order_system.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void createTransaction_success() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setProductId("p001");
        request.setQuantity(2);

        ProductResponse productResponse = ProductResponse.builder()
                .id("p001")
                .name("Laptop")
                .price(10_000_000L)
                .stock(5)
                .build();

        TransactionResponse response = TransactionResponse.builder()
                .id("t001")
                .product(productResponse)
                .quantity(2)
                .createdAt(LocalDateTime.now())
                .build();

        when(transactionService.createTransaction(any())).thenReturn(response);

        mockMvc.perform(post("/e-commerce/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value("t001"))
                .andExpect(jsonPath("$.data.product.id").value("p001"))
                .andExpect(jsonPath("$.data.quantity").value(2));
    }

    @Test
    void getTransaction_success() throws Exception {
        ProductResponse productResponse = ProductResponse.builder()
                .id("p001")
                .name("Laptop")
                .price(10_000_000L)
                .stock(5)
                .build();

        TransactionResponse response = TransactionResponse.builder()
                .id("t001")
                .product(productResponse)
                .quantity(2)
                .createdAt(LocalDateTime.now())
                .build();

        when(transactionService.getTransaction("t001")).thenReturn(response);

        mockMvc.perform(get("/e-commerce/transactions/{transaction_id}", "t001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value("t001"))
                .andExpect(jsonPath("$.data.product.id").value("p001"))
                .andExpect(jsonPath("$.data.quantity").value(2));
    }

    @Test
    void getAllTransaction_success() throws Exception {
        ProductResponse productResponse = ProductResponse.builder()
                .id("p001")
                .name("Laptop")
                .price(10_000_000L)
                .stock(5)
                .build();

        List<TransactionResponse> responses = List.of(
                TransactionResponse.builder()
                        .id("t001")
                        .product(productResponse)
                        .quantity(2)
                        .createdAt(LocalDateTime.now())
                        .build()
        );

        when(transactionService.getAllTransaction()).thenReturn(responses);

        mockMvc.perform(get("/e-commerce/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value("t001"))
                .andExpect(jsonPath("$.data[0].product.id").value("p001"))
                .andExpect(jsonPath("$.data[0].quantity").value(2));
    }
}
