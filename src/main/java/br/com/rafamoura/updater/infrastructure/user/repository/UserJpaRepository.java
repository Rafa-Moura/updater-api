package br.com.rafamoura.updater.infrastructure.user.repository;

import br.com.rafamoura.updater.domain.user.abstractions.repository.UserRepository;
import br.com.rafamoura.updater.domain.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>, UserRepository {

    @Query("select u from User u where u.registerStatus = 'PENDING'")
    List<User> findPendingUsers();

}