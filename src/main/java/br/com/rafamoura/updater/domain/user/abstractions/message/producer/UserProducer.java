package br.com.rafamoura.updater.domain.user.abstractions.message.producer;

import br.com.rafamoura.updater.domain.user.entities.User;

public interface UserProducer {

    void userUpdateProducer(User user);

}
