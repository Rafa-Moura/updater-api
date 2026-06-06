package br.com.rafamoura.updater.domain.user.service;

import br.com.rafamoura.updater.application.user.dto.ExternalClientResponseDTO;
import br.com.rafamoura.updater.domain.user.abstractions.clients.ExternalClient;
import br.com.rafamoura.updater.domain.user.abstractions.message.producer.UserProducer;
import br.com.rafamoura.updater.domain.user.abstractions.repository.UserRepository;
import br.com.rafamoura.updater.domain.user.abstractions.service.UserService;
import br.com.rafamoura.updater.domain.user.entities.User;
import br.com.rafamoura.updater.domain.user.enums.RegisterStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserProducer userProducer;
    private final ExternalClient externalClient;

    private final ExecutorService customExecutor = Executors.newFixedThreadPool(10);

    @Override
    public void findUsers() {
        log.info("[UserServiceImpl.findUsers] - Starting incomplete users search");

        List<User> pendingUsers = userRepository.findPendingUsers();

        if (pendingUsers.isEmpty()) {
            log.warn("[UserServiceImpl.findUsers] - Incomplete users not found");

            return;
        }

        pendingUsers.forEach(user ->
                CompletableFuture.runAsync(() -> updateAndSendUser(user), customExecutor));

        log.info("[UserServiceImpl.findUsers] - Finishing incomplete users search. [{}] users sent to update",
                pendingUsers.size());
    }

    @Override
    public void updateUser(User user) {
        log.info("[UserServiceImpl.updateUser] - Updating user: [{}]",
                user.getId());

        try {
            ExternalClientResponseDTO streetAddress = externalClient.getStreetAddress(user.getEmail());

            user.setStreetAddress(streetAddress.streetAddress());
            updateUserStatus(RegisterStatusEnum.COMPLETED, user);

            log.info("[UserServiceImpl.updateUser] - User: [{}] updated. StreetAddress: [{}]",
                    user.getId(), user.getStreetAddress());

        } catch (HttpClientErrorException ex) {
            log.error("[UserServiceImpl.updateUser] - HttpClientErrorException was occurred during updateUser: [{}]", ex.getMessage());

            updateUserStatus(RegisterStatusEnum.PENDING, user);

        } catch (Exception ex) {
            log.error("[UserServiceImpl.updateUser] - GenericException was occurred during updateUser: [{}]",
                    ex.getMessage());

            updateUserStatus(RegisterStatusEnum.ERROR, user);
        }
    }

    private void updateAndSendUser(User user) {
        try {
            updateUserStatus(RegisterStatusEnum.ON_PROCESS, user);

            userProducer.userUpdateProducer(user);
        } catch (AmqpException ex) {
            log.error("[UserServiceImpl.updateAndSendUser] - UserProducer throws an AmqpException. Reverting status to: [{}]",
                    RegisterStatusEnum.ON_PROCESS.name(), ex);

            updateUserStatus(RegisterStatusEnum.PENDING, user);
        } catch (Exception ex) {
            log.error("[UserServiceImpl.updateAndSendUser] - UserProducer throws an exception. Reverting status to: [{}]",
                    RegisterStatusEnum.ERROR.name(), ex);

            updateUserStatus(RegisterStatusEnum.ERROR, user);
        }
    }

    private void updateUserStatus(RegisterStatusEnum statusEnum, User user) {
        user.setRegisterStatus(statusEnum);
        userRepository.save(user);
    }
}
