package org.example.repository;

import org.example.model.criteria.PageCriteria;
import org.example.model.criteria.UserSearchCriteria;
import org.example.model.Users;
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
public class UserCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public UserCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public org.springframework.data.domain.Page findAllWithFilters(PageCriteria pageCriteria, UserSearchCriteria userSearchCriteria) {
        CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
        Root<Users> userRoot = criteriaQuery.from(Users.class);
        Predicate predicate = getPredicate(userSearchCriteria, userRoot);
        criteriaQuery.where(predicate);
        setOrder(pageCriteria, criteriaQuery, userRoot);

        TypedQuery<Users> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageCriteria.getPageNumber() * pageCriteria.getPageSize());
        typedQuery.setMaxResults(pageCriteria.getPageSize());

        Pageable pageable = getPageable(pageCriteria);
        long usersCount = getUsersCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, usersCount);
    }

    private Predicate getPredicate(UserSearchCriteria userSearchCriteria, Root<Users> usersRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(userSearchCriteria.getName())) {
            predicates.add(criteriaBuilder.like(usersRoot.get("name"), "%" + userSearchCriteria.getName() + "%"));
        }
        if (Objects.nonNull(userSearchCriteria.getSurname())) {
            predicates.add(criteriaBuilder.like(usersRoot.get("surname"), "%" + userSearchCriteria.getSurname() + "%"));
        }
        if (Objects.nonNull(userSearchCriteria.getEmail())) {
            predicates.add(criteriaBuilder.like(usersRoot.get("email"), "%" + userSearchCriteria.getEmail() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PageCriteria pageCriteria, CriteriaQuery<Users> criteriaQuery, Root<Users> userRoot) {
        if (pageCriteria.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(userRoot.get(pageCriteria.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(userRoot.get(pageCriteria.getSortBy())));
        }
    }

    private Pageable getPageable(PageCriteria pageCriteria) {
        Sort sort = Sort.by(pageCriteria.getSortDirection(), pageCriteria.getSortBy());
        return PageRequest.of(pageCriteria.getPageNumber(), pageCriteria.getPageSize(), sort);
    }

    private long getUsersCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Users> countRoot = countQuery.from(Users.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }


}
