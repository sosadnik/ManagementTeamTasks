package org.example.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Task {

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
