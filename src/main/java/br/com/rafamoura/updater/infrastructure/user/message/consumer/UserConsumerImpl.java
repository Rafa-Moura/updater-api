package br.com.rafamoura.updater.infrastructure.user.message.consumer;

import br.com.rafamoura.updater.domain.user.abstractions.message.consumer.UserConsumer;
import br.com.rafamoura.updater.domain.user.abstractions.service.UserService;
import br.com.rafamoura.updater.domain.user.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserConsumerImpl implements UserConsumer {

    private final UserService userService;

    @Override
    @RabbitListener(queues = "updater.queue")
    public void updateUserConsume(User user) {

        userService.updateUser(user);

    }
}
