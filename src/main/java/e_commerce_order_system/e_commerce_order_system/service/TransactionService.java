package e_commerce_order_system.e_commerce_order_system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import e_commerce_order_system.e_commerce_order_system.entity.Product;
import e_commerce_order_system.e_commerce_order_system.entity.Transaction;
import e_commerce_order_system.e_commerce_order_system.exception.EntityNotFoundException;
import e_commerce_order_system.e_commerce_order_system.messaging.OrderProducer;
import e_commerce_order_system.e_commerce_order_system.model.request.request_transaction.CreateTransactionRequest;
import e_commerce_order_system.e_commerce_order_system.model.response.response_product.ProductResponse;
import e_commerce_order_system.e_commerce_order_system.model.response.response_transaction.TransactionResponse;
import e_commerce_order_system.e_commerce_order_system.repository.ProductRepository;
import e_commerce_order_system.e_commerce_order_system.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

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

        // trigger async task
        orderProducer.senOrderProcessed(transaction.getId());

        return TransactionResponse.builder()
                .id(transaction.getId())
                .product(
                        ProductResponse.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .build()
                )
                .quantity(transaction.getQuantity())
                .createdAt(transaction.getCreatedAt())
                .build();
    }


    @Transactional
    public TransactionResponse getTransaction(String transactionId) {

        String cacheKey = "transactions:" + transactionId;

        //check data from redis
        Object cachedData = redisTemplate.opsForValue().get(cacheKey);

        if (cachedData != null){
            log.info("🔥 CACHE HIT {}", cacheKey);

            if (cachedData instanceof TransactionResponse transactionResponse){
                return  transactionResponse;
            }

            return objectMapper.convertValue(cachedData,TransactionResponse.class);
        }

        log.info("❄️ CACHE MISS {}", cacheKey);

        // QUERY to DB
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Transaction with id " + transactionId + " not found"
                ));

        Product product = transaction.getProduct();

        TransactionResponse response = TransactionResponse.builder()
                .id(transaction.getId())
                .product(
                        ProductResponse.builder()
                                .id(product.getId())
                                .name(product.getName())
                                .price(product.getPrice())
                                .stock(product.getStock())
                                .build()
                )
                .quantity(transaction.getQuantity())
                .createdAt(transaction.getCreatedAt())
                .build();

        // SAVE TO REDIS
        redisTemplate.opsForValue().set(
                cacheKey,
                response,
                Duration.ofMinutes(10)
        );

        return response;
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllTransaction() {

        String cacheKey = "transactions:all";

        //check data from redis
        Object cachedData = redisTemplate.opsForValue().get(cacheKey);

        if (cachedData instanceof List<?> list) {
            log.info("🔥 CACHE HIT {}", cacheKey);

            return list.stream()
                    .map(item -> {
                        if (item instanceof TransactionResponse transactionResponse) {
                            return transactionResponse;
                        }
                        return objectMapper.convertValue(item, TransactionResponse.class);
                    })
                    .toList();
        }

        log.info("❄️ CACHE MISS {}", cacheKey);

        // QUERY to DB
        List<TransactionResponse> responses = transactionRepository.findAll()
                .stream()
                .map(transaction -> {
                    Product product = transaction.getProduct();

                    return TransactionResponse.builder()
                            .id(transaction.getId())
                            .product(
                                    ProductResponse.builder()
                                            .id(product.getId())
                                            .name(product.getName())
                                            .price(product.getPrice())
                                            .stock(product.getStock())
                                            .build()
                            )
                            .quantity(transaction.getQuantity())
                            .createdAt(transaction.getCreatedAt())
                            .build();
                })
                .toList();

        redisTemplate.opsForValue()
                .set(cacheKey, responses, Duration.ofMinutes(5));

        return responses;
    }


}
