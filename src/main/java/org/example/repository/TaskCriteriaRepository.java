package org.example.repository;

import org.example.model.*;
import org.example.model.criteria.PageCriteria;
import org.example.model.criteria.TaskSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TaskCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public TaskCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Task> findAllWithFilters(PageCriteria pageCriteria, TaskSearchCriteria taskSearchCriteria) {
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        Root<Task> userRoot = criteriaQuery.from(Task.class);
        Predicate predicate = getPredicate(taskSearchCriteria, userRoot);
        criteriaQuery.where(predicate);
        setOrder(pageCriteria, criteriaQuery, userRoot);

        TypedQuery<Task> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageCriteria.getPageNumber() * pageCriteria.getPageSize());
        typedQuery.setMaxResults(pageCriteria.getPageSize());

        Pageable pageable = getPageable(pageCriteria);
        long usersCount = getUsersCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, usersCount);
    }

    private Predicate getPredicate(TaskSearchCriteria taskSearchCriteria, Root<Task> usersRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(taskSearchCriteria.getTitle())) {
            predicates.add(criteriaBuilder.like(usersRoot.get("title"), "%" + taskSearchCriteria.getTitle() + "%"));
        }
        if (Objects.nonNull(taskSearchCriteria.getStatus())) {
            predicates.add(criteriaBuilder.like(usersRoot.get("status"), "%" + taskSearchCriteria.getStatus()+ "%"));
        }
        if (Objects.nonNull(taskSearchCriteria.getTimeLimit())) {
            predicates.add(criteriaBuilder.like(usersRoot.get("timeLimit"), "%" + taskSearchCriteria.getTimeLimit() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PageCriteria pageCriteria, CriteriaQuery<Task> criteriaQuery, Root<Task> taskRoot) {
        if (pageCriteria.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(taskRoot.get(pageCriteria.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(taskRoot.get(pageCriteria.getSortBy())));
        }
    }

    private Pageable getPageable(PageCriteria pageCriteria) {
        Sort sort = Sort.by(pageCriteria.getSortDirection(), pageCriteria.getSortBy());
        return PageRequest.of(pageCriteria.getPageNumber(), pageCriteria.getPageSize(), sort);
    }

    private long getUsersCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Task> countRoot = countQuery.from(Task.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
