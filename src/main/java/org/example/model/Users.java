package org.example.model;

import javax.persistence.*;

@Entity
public class Users {

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

}
