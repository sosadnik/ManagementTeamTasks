package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Users;
import org.example.model.criteria.PageCriteria;
import org.example.model.criteria.UserSearchCriteria;
import org.example.model.dto.UserDto;
import org.example.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    public ResponseEntity<Users> addUser(UserDto user) {
        return new ResponseEntity<>(service.addUser(user), HttpStatus.OK);
    }

    public HttpStatus deleteUser(Long id) {
        if (service.deleteUser(id)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public ResponseEntity<Page<Users>> searchUser(PageCriteria pageCriteria, UserSearchCriteria userSearchCriteria) {
        return new ResponseEntity<>(service.search(pageCriteria, userSearchCriteria),
                HttpStatus.OK);
    }

}
