package e_commerce_order_system.e_commerce_order_system.messaging;

import e_commerce_order_system.e_commerce_order_system.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RabbitListener(queues = RabbitConfig.ORDER_QUEUE)
    public void processOrder(OrderMessage message) throws InterruptedException {

        System.out.println("📥 Transaction received: " + message.getOrderId());

        String key = "order:processed:" + message.getOrderId();

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            System.out.println("⚠️ Transaction already processed");
            return;
        }

        Thread.sleep(5000);

        System.out.println("⚙️ Transaction processed: " + message.getOrderId());

        redisTemplate.opsForValue().set(key, "DONE");

        System.out.println("✅ Transaction success: " + message.getOrderId());
    }


}
