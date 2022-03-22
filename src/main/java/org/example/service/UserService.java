package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.UserDto;
import org.example.model.Task;
import org.example.model.criteria.PageCriteria;
import org.example.model.criteria.UserSearchCriteria;
import org.example.model.Users;
import org.example.repository.UserCriteriaRepository;
import org.example.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCriteriaRepository userCriteriaRepository;

    public Users addUser(UserDto user) {
        return userRepository.save(new Users(
                user.getName(),
                user.getSurname(),
                user.getEmail()
        ));
    }

    public boolean deleteUser(Long id) {
        Optional<Users> users = userRepository.findById(id);
        if (users.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else return false;
    }

    public void deleteTaskFromUser(Optional<Task> task) {
        List<Users> usersList = userRepository.findAllByTask(task);
        for (Users x : usersList) {
            x.setTask(null);
        }
        userRepository.saveAll(usersList);
    }

    public boolean assignUserToTask(Task task, Long idUser) {
        Optional<Users> users = userRepository.findById(idUser);
        if (users.isPresent()) {
            users.get().setTask(task);
            userRepository.save(users.get());
            return true;
        } else return false;
    }

    public Page<Users> search(PageCriteria pageCriteria, UserSearchCriteria userSearchCriteria) {
        return userCriteriaRepository.findAllWithFilters(pageCriteria, userSearchCriteria);
    }
}
