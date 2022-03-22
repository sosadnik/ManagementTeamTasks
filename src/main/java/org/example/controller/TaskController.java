package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.Task;
import org.example.model.criteria.PageCriteria;
import org.example.model.criteria.TaskSearchCriteria;
import org.example.model.dto.TaskDto;
import org.example.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    public ResponseEntity<Task> addTask(TaskDto task) {
        return new ResponseEntity<>(service.addTask(task),
                HttpStatus.ACCEPTED);
    }

    public HttpStatus deleteTask(Long id) {
        if (service.deleteTask(id)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public ResponseEntity<Task> changeStatus(Long id, String status) {
        Task task = service.changeStatus(id, status);
        if (Objects.nonNull(task)) {
            return ResponseEntity.ok().body(task);
        } else return ResponseEntity.notFound().build();

    }

    public HttpStatus assignUser(Long idTask, Long idUser) {
        if (service.assignUser(idTask, idUser)) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public ResponseEntity<Page<Task>> searchTask(PageCriteria pageCriteria, TaskSearchCriteria taskSearchCriteria) {
        return new ResponseEntity<>(service.search(pageCriteria, taskSearchCriteria),
                HttpStatus.OK);
    }


}
