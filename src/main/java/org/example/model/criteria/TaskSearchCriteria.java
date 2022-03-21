package org.example.model.criteria;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class TaskSearchCriteria {

    private String title;
    private String status;
    private Timestamp timeLimit;
}
