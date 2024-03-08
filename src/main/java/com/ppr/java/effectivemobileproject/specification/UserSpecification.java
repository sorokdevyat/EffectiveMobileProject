package com.ppr.java.effectivemobileproject.specification;

import com.ppr.java.effectivemobileproject.dto.userFilter.UserFilter;
import com.ppr.java.effectivemobileproject.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> getSpec(UserFilter filter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!ObjectUtils.isEmpty(filter.getFullName())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("fullname")),
                        String.format("%%%s%%", filter.getFullName().toLowerCase())));
            }

            if (!ObjectUtils.isEmpty(filter.getEmail())) {
                predicates.add(criteriaBuilder.isMember(filter.getEmail(), root.get("emails")));

//                predicates.add(criteriaBuilder.equal(
//                        criteriaBuilder.lower(root.get("email")),
//                        String.format("%s", filter.getEmail())));
            }
            if (!ObjectUtils.isEmpty(filter.getPhone())) {
                predicates.add(criteriaBuilder.isMember(filter.getPhone(), root.get("phoneNumbers")));
//
//                predicates.add(criteriaBuilder.equal(
//                        criteriaBuilder.lower(root.get("phoneNumbers")),
//                        String.format("%s", filter.getPhone())));
            }
            if (!ObjectUtils.isEmpty(filter.getDateOfBirth())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dateOfBirth"), filter.getDateOfBirth()));
            }
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
        });

    }
}
