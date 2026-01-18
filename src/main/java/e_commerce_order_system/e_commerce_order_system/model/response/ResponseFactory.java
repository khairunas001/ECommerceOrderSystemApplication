package e_commerce_order_system.e_commerce_order_system.model.response;

import e_commerce_order_system.e_commerce_order_system.model.web_response.WebResponseSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseFactory {

    public static <T> ResponseEntity<WebResponseSuccess<T>> created(T data) {

        WebResponseSuccess<T> response =
                WebResponseSuccess.<T>builder()
                        .status(HttpStatus.CREATED.toString()) // "201 CREATED"
                        .data(data)
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public static <T> ResponseEntity<WebResponseSuccess<T>> ok(T data) {

        WebResponseSuccess<T> response =
                WebResponseSuccess.<T>builder()
                        .status(HttpStatus.OK.toString()) // "200 OK"
                        .data(data)
                        .build();

        return ResponseEntity.ok(response);
    }
}
