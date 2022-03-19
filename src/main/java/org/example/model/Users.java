package org.example.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Users {

    public Users(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;

    }

    @Id
    @SequenceGenerator(
            name = "user_id_sequence",
            sequenceName = "user_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    private Long id;
    private String name;
    private String surname;
    private String email;
    @ManyToOne
    private Task task;

    public void setTask(Task task) {
        this.task = task;
    }
}
