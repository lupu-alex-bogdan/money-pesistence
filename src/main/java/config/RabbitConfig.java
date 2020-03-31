package config;

import consumer.QueueConsumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static final String LISTENER_METHOD = "receiveMessage";
    @Value("${exchange.name}")
    private String exchangeName;
    @Value("${queue.name}")
    private String queueName;
    @Value("${exchange.name.dlx}")
    private String exchangeNameDlx;
    @Value("${routing.key.dlx}")
    private String routingKeyDlx;
    @Value("${routing.key}")
    private String routingKey;

    @Bean
    Queue queue() {
        return QueueBuilder.durable(queueName)
                .deadLetterExchange(exchangeNameDlx)
                .deadLetterRoutingKey(routingKeyDlx)
                .ttl(15000)
                .build();
    }

    @Bean
    Exchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(QueueConsumer consumer) {
        return new MessageListenerAdapter(consumer, LISTENER_METHOD);
    }
}