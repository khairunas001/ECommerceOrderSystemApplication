package e_commerce_order_system.e_commerce_order_system.model.web_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WebResponseError<T> {

    private String status;

    private T data;
}
