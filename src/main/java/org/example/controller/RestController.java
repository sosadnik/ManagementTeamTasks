package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Task;
import org.example.model.Users;
import org.example.model.criteria.PageCriteria;
import org.example.model.criteria.TaskSearchCriteria;
import org.example.model.criteria.UserSearchCriteria;
import org.example.model.dto.TaskDto;
import org.example.model.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class RestController {

    private final TaskController taskController;
    private final UserController userController;

    @PostMapping("/user")
    public ResponseEntity<Users> addUser(UserDto user) {
        return userController.addUser(user);
    }

    public HttpStatus deleteUser(Long id) {
        return userController.deleteUser(id);
    }

    public ResponseEntity<Page<Users>> searchUser(PageCriteria pageCriteria, UserSearchCriteria userSearchCriteria) {
        return userController.searchUser(pageCriteria, userSearchCriteria);
    }

    public ResponseEntity<Task> addTask(TaskDto task) {
        return taskController.addTask(task);
    }

    public HttpStatus deleteTask(Long id) {
        return taskController.deleteTask(id);
    }

    public ResponseEntity<Task> changeStatus(Long id, String status) {
        return taskController.changeStatus(id, status);
    }

    public HttpStatus assignUser(Long idTask, Long idUser) {
        return taskController.assignUser(idTask, idUser);
    }

    public ResponseEntity<Page<Task>> searchTask(PageCriteria pageCriteria, TaskSearchCriteria taskSearchCriteria) {
        return taskController.searchTask(pageCriteria, taskSearchCriteria);
    }
}
