package e_commerce_order_system.e_commerce_order_system.model.request.request_transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateTransactionRequest {

    @NotBlank
    @JsonProperty("product_id")
    private String productId;

    @NotNull
    private Integer quantity;
}
