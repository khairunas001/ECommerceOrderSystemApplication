package e_commerce_order_system.e_commerce_order_system.model.request.request_product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateProductRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long price;

    @NotNull
    private Integer stock;


}
