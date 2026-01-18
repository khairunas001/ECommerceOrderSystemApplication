package e_commerce_order_system.e_commerce_order_system.controller;

import e_commerce_order_system.e_commerce_order_system.model.request.request_transaction.CreateTransactionRequest;
import e_commerce_order_system.e_commerce_order_system.model.response.ResponseFactory;
import e_commerce_order_system.e_commerce_order_system.model.response.response_transaction.TransactionResponse;
import e_commerce_order_system.e_commerce_order_system.model.web_response.WebResponseSuccess;
import e_commerce_order_system.e_commerce_order_system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping(path = "/e-commerce/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponseSuccess<TransactionResponse>> createTransaction(@RequestBody CreateTransactionRequest request) {

        TransactionResponse response = transactionService.createTransaction(request);

        return ResponseFactory.created(response);
    }

    @GetMapping(path = "/e-commerce/transactions/{transaction_id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponseSuccess<TransactionResponse>> getTransaction(@PathVariable(name = "transaction_id" ) String transactionId) {

        TransactionResponse response = transactionService.getTransaction(transactionId);

        return ResponseFactory.ok(response);
    }

    @GetMapping(path = "/e-commerce/transactions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private ResponseEntity<WebResponseSuccess<List<TransactionResponse>>> getAllTransaction() {

        List<TransactionResponse> response = transactionService.getAllTransaction();

        return ResponseFactory.ok(response);
    }
}
