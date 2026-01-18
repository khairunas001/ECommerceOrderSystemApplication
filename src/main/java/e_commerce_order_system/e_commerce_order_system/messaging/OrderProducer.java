package e_commerce_order_system.e_commerce_order_system.messaging;

import e_commerce_order_system.e_commerce_order_system.config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class OrderProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void senOrderProcessed (String orderId){

        rabbitTemplate.convertAndSend(RabbitConfig.ORDER_QUEUE, new OrderMessage(orderId));

    }

}
