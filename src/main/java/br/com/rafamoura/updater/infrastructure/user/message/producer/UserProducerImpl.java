package br.com.rafamoura.updater.infrastructure.user.message.producer;

import br.com.rafamoura.updater.domain.user.abstractions.message.producer.UserProducer;
import br.com.rafamoura.updater.domain.user.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserProducerImpl implements UserProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void userUpdateProducer(User user) {

        rabbitTemplate.convertAndSend("updater.ex", "updater.queue", user);

    }
}
