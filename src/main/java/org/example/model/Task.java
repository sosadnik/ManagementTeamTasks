package org.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

}
