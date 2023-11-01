package com.hrms.usermanagement.utils;//package com.hrms.usermanagement.utils;
//
//import com.hrms.usermanagement.model.User;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserSpecifications {
//    public static Specification<User> userEqualsRoleId(int roleId) {
//        return new Specification<User>() {
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get(User_.get))
//            }
//        }
//    }
//}
