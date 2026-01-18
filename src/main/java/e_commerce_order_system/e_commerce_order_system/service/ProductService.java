package e_commerce_order_system.e_commerce_order_system.service;

import com.fasterxml.jackson.core.type.TypeReference;
import e_commerce_order_system.e_commerce_order_system.entity.Product;
import e_commerce_order_system.e_commerce_order_system.exception.EntityNotFoundException;
import e_commerce_order_system.e_commerce_order_system.model.request.request_product.CreateProductRequest;
import e_commerce_order_system.e_commerce_order_system.model.request.request_product.UpdateProductRequest;
import e_commerce_order_system.e_commerce_order_system.model.response.response_product.ProductResponse;
import e_commerce_order_system.e_commerce_order_system.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {

        validationService.validate(request);

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();

    }

    @Transactional
    public ProductResponse getProduct(String productId) {

        String cacheKey = "product:" + productId;

        // check the product from redis
        Object cachedData = redisTemplate.opsForValue().get(cacheKey);

        if (cachedData != null) {
            log.info("🔥 CACHE HIT {}", cacheKey);

            if (cachedData instanceof ProductResponse productResponse) {
                return productResponse;
            }

            return objectMapper.convertValue(cachedData, ProductResponse.class);

        }

        // Cache MISS → query DB
        log.info("❄️ CACHE MISS {}", cacheKey);


        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();

        // save to redis
        redisTemplate.opsForValue().set(cacheKey,
                response,
                Duration.ofMinutes(10));

        return response;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProduct() {

        String cacheKey = "products:all";

        Object cachedData = redisTemplate.opsForValue().get(cacheKey);

        if (cachedData != null) {
            log.info("🔥 CACHE HIT products:all");

            return objectMapper.convertValue(
                    cachedData,
                    new TypeReference<List<ProductResponse>>() {
                    }
            );
        }

        log.info("💾 CACHE MISS → HIT DATABASE");

        List<ProductResponse> responses = productRepository.findAll()
                .stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .stock(product.getStock())
                        .build())
                .toList();

        redisTemplate.opsForValue()
                .set(cacheKey, responses, Duration.ofMinutes(10));

        return responses;
    }

    @Transactional
    public void deleteProduct(String productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        productRepository.delete(product);
    }

    @Transactional
    public ProductResponse updateProduct(String productId, UpdateProductRequest request) {

        validationService.validate(request);

        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product with id " + productId + " not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }


}
