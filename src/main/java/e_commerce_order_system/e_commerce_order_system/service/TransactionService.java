package e_commerce_order_system.e_commerce_order_system.service;

import e_commerce_order_system.e_commerce_order_system.entity.Product;
import e_commerce_order_system.e_commerce_order_system.entity.Transaction;
import e_commerce_order_system.e_commerce_order_system.exception.EntityNotFoundException;
import e_commerce_order_system.e_commerce_order_system.model.request.request_transaction.CreateTransactionRequest;
import e_commerce_order_system.e_commerce_order_system.model.response.response_transaction.TransactionResponse;
import e_commerce_order_system.e_commerce_order_system.repository.ProductRepository;
import e_commerce_order_system.e_commerce_order_system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TrasactionSevice {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {

        validationService.validate(request);

        // is product exist?
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new EntityNotFoundException("Product with id " + request.getProductId() + " not found"));

        // check if stock is enough
        if (request.getQuantity() > product.getStock()) {
            throw new IllegalArgumentException("Stock is not enough");
        }

        // reduce stock
        product.setStock(product.getStock() - request.getQuantity());

        // save updated product
        productRepository.save(product);

        // insert the transactiom
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setProduct(product);
        transaction.setQuantity(request.getQuantity());
        transaction.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(transaction);

        return TransactionResponse.builder().build();
    }

}
