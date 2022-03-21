package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Users;
import org.example.model.criteria.PageCriteria;
import org.example.model.criteria.UserSearchCriteria;
import org.example.model.dto.UserDto;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public Users addUser(UserDto user) {
        return service.adduser(user);

    }

    public void deleteUser(Long id) {
        service.deleteUser(id);
    }


    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page> searchUser(PageCriteria pageCriteria, UserSearchCriteria userSearchCriteria) {
        return new ResponseEntity<>(service.search(pageCriteria, userSearchCriteria),
                HttpStatus.OK);
    }

}
