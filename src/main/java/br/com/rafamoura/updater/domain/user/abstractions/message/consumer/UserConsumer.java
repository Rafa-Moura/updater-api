package br.com.rafamoura.updater.domain.user.abstractions.message.consumer;

import br.com.rafamoura.updater.domain.user.entities.User;

public interface UserConsumer {

    void updateUserConsume(User user);

}
