package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.dto.TaskDto;
import org.example.service.TaskService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class TaskController {


    private final TaskService service;

    public void addTask(TaskDto task) {
        service.addTask(task);
    }

    public void deleteTask(Long id) {
        service.deleteTask(id);
    }

    public void changeStatus(Long id, String status) {
        service.changeStatus(id, status);
    }

    public void assignUser(Long idTask, Long idUser) {
        service.assignUser(idTask, idUser);
    }

    public void findTask() {

    }


}
