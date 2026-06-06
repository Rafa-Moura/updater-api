package br.com.rafamoura.updater.domain.user.abstractions.service;

import br.com.rafamoura.updater.domain.user.entities.User;

public interface UserService {

    void findUsers();
    void updateUser(User user);

}
