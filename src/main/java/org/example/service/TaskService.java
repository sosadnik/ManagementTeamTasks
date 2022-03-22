package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.criteria.PageCriteria;
import org.example.model.criteria.TaskSearchCriteria;
import org.example.model.dto.TaskDto;
import org.example.model.Task;
import org.example.repository.TaskCriteriaRepository;
import org.example.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCriteriaRepository taskCriteriaRepository;
    private final UserService userService;

    public Task addTask(TaskDto task) {
        return taskRepository.save(new Task(
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getTimeLimit()
        ));
    }

    @Transactional
    public boolean deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            userService.deleteTaskFromUser(task);
            taskRepository.deleteById(id);
            return true;
        } else return false;
    }

    public Task changeStatus(Long id, String status) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            task.get().setStatus(status);
            return taskRepository.save(task.get());
        } else return new Task();

    }

    public boolean assignUser(Long idTask, Long idUser) {
        Optional<Task> optionalTask = taskRepository.findById(idTask);
        optionalTask.ifPresent(task -> userService.assignUserToTask(task, idUser));
        return optionalTask.filter(task -> userService.assignUserToTask(task, idUser)).isPresent();
    }

    public Page<Task> search(PageCriteria pageCriteria, TaskSearchCriteria taskSearchCriteria) {
        return taskCriteriaRepository.findAllWithFilters(pageCriteria, taskSearchCriteria);
    }
}
