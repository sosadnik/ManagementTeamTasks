package org.example.model.criteria;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PageCriteria {
    private int pageNumber = 0;
    private int pageSize = 5;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "id";
}
