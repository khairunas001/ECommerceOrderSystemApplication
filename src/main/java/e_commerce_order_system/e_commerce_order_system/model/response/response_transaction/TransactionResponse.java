package e_commerce_order_system.e_commerce_order_system.model.response.response_transaction;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import e_commerce_order_system.e_commerce_order_system.entity.Product;
import e_commerce_order_system.e_commerce_order_system.model.response.response_product.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonPropertyOrder({
        "id",
        "product",
        "quantity",
        "createdAt"
})
public class TransactionResponse {

    private String id;

    private ProductResponse product;

    private Integer quantity;

    private LocalDateTime createdAt;
}
