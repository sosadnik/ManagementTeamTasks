package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.controller.dto.UserDto;
import org.example.model.Task;
import org.example.model.Users;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Users adduser(UserDto user) {
        return repository.save(new Users(
                user.getName(),
                user.getSurname(),
                user.getEmail()
        ));
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    public void deleteTaskFromUser(Optional<Task> task) {
        List<Users> usersList = repository.findAllByTask(task);
        for (Users x : usersList){
            x.setTask(null);
        }
        repository.saveAll(usersList);
    }

    public void assignUserToTask(Task task, Long idUser) {
        Optional<Users> users = repository.findById(idUser);
        users.ifPresent(value -> value.setTask(task));
    }
}
