package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.controller.dto.TaskDto;
import org.example.model.Task;
import org.example.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository repository;
    private final UserService userService;

    public Task addTask(TaskDto task) {
        return repository.save(new Task(
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getTimeLimit()
        ));
    }

    @Transactional
    public void deleteTask(Long id) {
        userService.deleteTaskFromUser(repository.findById(id));
        repository.deleteById(id);
    }

    public void changeStatus(Long id, String status) {
        Optional<Task> task = repository.findById(id);
        if (task.isPresent()) {
            task.get().setStatus(status);
            repository.save(task.get());
        }

    }

    public void assignUser(Long idTask, Long idUser) {
        Optional<Task> optionalTask = repository.findById(idTask);
        optionalTask.ifPresent(task -> userService.assignUserToTask(task, idUser));
    }
}
