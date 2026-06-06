package br.com.rafamoura.updater.application.user.controller;

import br.com.rafamoura.updater.domain.user.abstractions.repository.UserRepository;
import br.com.rafamoura.updater.domain.user.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping()
    public List<User> find() {
        return userRepository.findAll();
    }

}
