package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.dto.UserDto;
import org.example.model.Users;
import org.example.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    public Users addUser(UserDto user) {
        return service.adduser(user);

    }

    public void deleteUser(Long id) {
        service.deleteUser(id);
    }

    public void findUser() {

    }

}
