package e_commerce_order_system.e_commerce_order_system.model.request.request_product;

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
public class UpdateProductRequest {

    private String name;

    private Long price;

    private Integer stock;

}
