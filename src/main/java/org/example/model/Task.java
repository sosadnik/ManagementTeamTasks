package org.example.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Task {

    public Task(String title, String description, String status, Timestamp timeLimit) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.timeLimit = timeLimit;
    }

    @Id
    @SequenceGenerator(
            name = "task_id_sequence",
            sequenceName = "task_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_id_sequence"
    )
    private Long id;
    private String title;
    private String description;
    private String status;
    private Timestamp timeLimit;
    @OneToMany(mappedBy = "task")
    private Set<Users> assignedUsers;

    public void setStatus(String status) {
        this.status = status;
    }
}
