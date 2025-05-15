package me.diegxherrera.backcoachservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Coach Event Queues and Routing
    public static final String COACH_CREATED_QUEUE = "coach.created.queue";
    public static final String COACH_UPDATED_QUEUE = "coach.updated.queue";
    public static final String COACH_DELETED_QUEUE = "coach.deleted.queue";
    public static final String COACH_EXCHANGE = "coach.exchange";
    public static final String COACH_CREATED_ROUTING_KEY = "coach.created";
    public static final String COACH_UPDATED_ROUTING_KEY = "coach.updated";
    public static final String COACH_DELETED_ROUTING_KEY = "coach.deleted";

    // Seat Event Queues and Routing
    public static final String SEAT_CREATED_QUEUE = "seat.created.queue";
    public static final String SEAT_UPDATED_QUEUE = "seat.updated.queue";
    public static final String SEAT_DELETED_QUEUE = "seat.deleted.queue";
    public static final String SEAT_EXCHANGE = "seat.exchange";
    public static final String SEAT_CREATED_ROUTING_KEY = "seat.created";
    public static final String SEAT_UPDATED_ROUTING_KEY = "seat.updated";
    public static final String SEAT_DELETED_ROUTING_KEY = "seat.deleted";

    // Declare Coach Event Queues
    @Bean
    public Queue coachCreatedQueue() {
        return new Queue(COACH_CREATED_QUEUE);
    }

    @Bean
    public Queue coachUpdatedQueue() {
        return new Queue(COACH_UPDATED_QUEUE);
    }

    @Bean
    public Queue coachDeletedQueue() {
        return new Queue(COACH_DELETED_QUEUE);
    }

    // Declare Seat Event Queues
    @Bean
    public Queue seatCreatedQueue() {
        return new Queue(SEAT_CREATED_QUEUE);
    }

    @Bean
    public Queue seatUpdatedQueue() {
        return new Queue(SEAT_UPDATED_QUEUE);
    }

    @Bean
    public Queue seatDeletedQueue() {
        return new Queue(SEAT_DELETED_QUEUE);
    }

    // Declare Topic Exchange for Coach Events
    @Bean
    public TopicExchange coachExchange() {
        return new TopicExchange(COACH_EXCHANGE);
    }

    // Declare Topic Exchange for Seat Events
    @Bean
    public TopicExchange seatExchange() {
        return new TopicExchange(SEAT_EXCHANGE);
    }

    // Bind the Coach Queues to Coach Exchange with routing keys
    @Bean
    public Binding coachCreatedBinding(Queue coachCreatedQueue, TopicExchange coachExchange) {
        return BindingBuilder.bind(coachCreatedQueue).to(coachExchange).with(COACH_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding coachUpdatedBinding(Queue coachUpdatedQueue, TopicExchange coachExchange) {
        return BindingBuilder.bind(coachUpdatedQueue).to(coachExchange).with(COACH_UPDATED_ROUTING_KEY);
    }

    @Bean
    public Binding coachDeletedBinding(Queue coachDeletedQueue, TopicExchange coachExchange) {
        return BindingBuilder.bind(coachDeletedQueue).to(coachExchange).with(COACH_DELETED_ROUTING_KEY);
    }

    // Bind the Seat Queues to Seat Exchange with routing keys
    @Bean
    public Binding seatCreatedBinding(Queue seatCreatedQueue, TopicExchange seatExchange) {
        return BindingBuilder.bind(seatCreatedQueue).to(seatExchange).with(SEAT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding seatUpdatedBinding(Queue seatUpdatedQueue, TopicExchange seatExchange) {
        return BindingBuilder.bind(seatUpdatedQueue).to(seatExchange).with(SEAT_UPDATED_ROUTING_KEY);
    }

    @Bean
    public Binding seatDeletedBinding(Queue seatDeletedQueue, TopicExchange seatExchange) {
        return BindingBuilder.bind(seatDeletedQueue).to(seatExchange).with(SEAT_DELETED_ROUTING_KEY);
    }

    // Jackson JSON Message Converter to convert objects to JSON and vice versa
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // RabbitTemplate to send messages to RabbitMQ
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}