package e_commerce_order_system.e_commerce_order_system.model.response.response_product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductResponse {

    private String id;

    private String name;

    private Long price;

    private Integer stock;
}
