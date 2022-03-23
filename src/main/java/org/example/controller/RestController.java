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
import org.springframework.web.bind.annotation.*;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
@RequestMapping("/management")
public class RestController {

    private final TaskController taskController;
    private final UserController userController;

    @PostMapping("/user")
    public ResponseEntity<Users> addUser(@RequestBody UserDto user) {
        return userController.addUser(user);
    }

    @DeleteMapping("/user/{id}")
    public HttpStatus deleteUser(@PathVariable Long id) {
        return userController.deleteUser(id);
    }

    @GetMapping("/user")
    public ResponseEntity<Page<Users>> searchUser(PageCriteria pageCriteria, UserSearchCriteria userSearchCriteria) {
        return userController.searchUser(pageCriteria, userSearchCriteria);
    }

    @PostMapping("/task")
    public ResponseEntity<Task> addTask(@RequestBody TaskDto task) {
        return taskController.addTask(task);
    }

    @DeleteMapping("/task/{id}")
    public HttpStatus deleteTask(@PathVariable Long id) {
        return taskController.deleteTask(id);
    }

    @PatchMapping("/task/status/{id}/{status}")
    public ResponseEntity<Task> changeStatus(@PathVariable Long id, @PathVariable String status) {
        return taskController.changeStatus(id, status);
    }

    @PatchMapping("/task/{idTask}/{idUser}")
    public HttpStatus assignUser(@PathVariable Long idTask, @PathVariable Long idUser) {
        return taskController.assignUser(idTask, idUser);
    }

    @GetMapping("/task")
    public ResponseEntity<Page<Task>> searchTask(PageCriteria pageCriteria, TaskSearchCriteria taskSearchCriteria) {
        return taskController.searchTask(pageCriteria, taskSearchCriteria);
    }
}
