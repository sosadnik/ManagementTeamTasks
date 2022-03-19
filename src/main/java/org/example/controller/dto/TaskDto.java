package org.example.controller.dto;

import lombok.Getter;
import org.example.model.Users;

import java.sql.Timestamp;
import java.util.Set;

@Getter
public class TaskDto {

    private String title;
    private String description;
    private String status;
    private Timestamp timeLimit;
    private Set<Users> assignedUsers;
}


