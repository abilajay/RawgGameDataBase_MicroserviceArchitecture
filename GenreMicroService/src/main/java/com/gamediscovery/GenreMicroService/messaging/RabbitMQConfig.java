package com.gamediscovery.GenreMicroService.messaging;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue genreQueue(){
        return new Queue("GenreQueue");
    }

    @Bean
    public Queue publisherQueue(){
        return new Queue("PublisherQueue");
    }

    @Bean
    public Queue updatedGameQueue(){
        return new Queue("UpdatedGameQueue");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange("GameExchange");
    }


    @Bean
    public Binding genreBinding(@Qualifier("genreQueue") Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("genre-routing-key");
    }

    @Bean
    public Binding publisherBinding(@Qualifier("publisherQueue") Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("publisher-routing-key");
    }

    @Bean
    public Binding updatedGameBinding(@Qualifier("updatedGameQueue") Queue queue, DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with("updated-game-routing-key");
    }


}
