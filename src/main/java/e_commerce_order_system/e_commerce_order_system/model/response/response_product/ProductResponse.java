package e_commerce_order_system.e_commerce_order_system.model.response.response_product;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateProductResponse {

    private String id;

    private String name;

    private Long price;

    private Integer stock;
}
