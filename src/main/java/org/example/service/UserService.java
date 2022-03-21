package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.UserDto;
import org.example.model.Task;
import org.example.model.criteria.PageCriteria;
import org.example.model.criteria.UserSearchCriteria;
import org.example.model.Users;
import org.example.repository.UserCriteriaRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCriteriaRepository userCriteriaRepository;

    public Users adduser(UserDto user) {
        return userRepository.save(new Users(
                user.getName(),
                user.getSurname(),
                user.getEmail()
        ));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteTaskFromUser(Optional<Task> task) {
        List<Users> usersList = userRepository.findAllByTask(task);
        for (Users x : usersList) {
            x.setTask(null);
        }
        userRepository.saveAll(usersList);
    }

    public void assignUserToTask(Task task, Long idUser) {
        Optional<Users> users = userRepository.findById(idUser);
        users.ifPresent(value -> value.setTask(task));
    }

    public org.springframework.data.domain.Page search(PageCriteria pageCriteria, UserSearchCriteria userSearchCriteria) {
        return userCriteriaRepository.findAllWithFilters(pageCriteria, userSearchCriteria);
    }
}
