package br.com.rafamoura.updater.domain.user.abstractions.repository;

import br.com.rafamoura.updater.domain.user.entities.User;

import java.util.List;

public interface UserRepository {
    List<User> findPendingUsers();
    User save(User user);
    List<User> findAll();
}
