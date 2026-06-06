package br.com.rafamoura.updater.infrastructure.user.message.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new JacksonJsonMessageConverter(JsonMapper.builder()
                .build());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    @Bean
    public Queue updateUserQueue() {
        return new Queue("updater.user", true);
    }

    @Bean
    public DirectExchange updateUserExchange() {
        return new DirectExchange("updater.ex");
    }

    @Bean
    public Binding binding(Queue updateUserQueue, DirectExchange updateUserExchange) {
        return BindingBuilder.bind(updateUserQueue).to(updateUserExchange).with("updater.user");
    }
}