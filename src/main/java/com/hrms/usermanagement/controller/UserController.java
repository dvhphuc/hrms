package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.dto.UserDtoModel;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.LinkBuilder;
import org.springframework.hateoas.server.core.ControllerEntityLinks;
import org.springframework.hateoas.server.core.ControllerEntityLinksFactoryBean;
import org.springframework.hateoas.server.core.LinkBuilderSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.LinkedHashMap;
import java.util.List;

@Controller
//@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

//    @GetMapping
//    public List<UserDto> getUsers() {
//        return userService.getAll();
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity<Page<UserDto>> getUsers(@RequestParam int pageNo, @RequestParam int pageSize) {
//        var usersDto = userService.getAll(PageRequest.of(pageNo, pageSize));
//        return ResponseEntity.ok(usersDto);
//    }
//
//    @GetMapping("/filter")
//    public Page<UserDto> getUsers(@RequestParam(required = false, value = "roleName") String roleName,
//                                  @RequestParam(required = false, value = "status") Boolean status,
//                                  @RequestParam int pageNo,
//                                  @RequestParam int pageSize) {
//        var roleNameFilter = new Specification<User>() {
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get("role").get("name"), roleName);
//            }
//        };
//
//        var statusFilter = new Specification<User>() {
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return criteriaBuilder.equal(root.get("isEnabled"), status);
//            }
//        };
//
//        var pageSortByCreatedAt = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
//        if (status == null)
//            return userService.getAllByFilter(roleNameFilter,pageSortByCreatedAt);
//        if (roleName == null)
//            return userService.getAllByFilter(statusFilter, pageSortByCreatedAt);
//        return userService.getAllByFilter(roleNameFilter.and(statusFilter), pageSortByCreatedAt);
//    }
//
//    @PutMapping("/update/{username}")
//    public ResponseEntity updateUser(@PathVariable String username,
//                                     @RequestParam(name = "roleName") String roleName,
//                                     @RequestParam(name = "isEnable") boolean isEnable)
//    {
//        userService.updateUser(username, roleName, isEnable);
//        return ResponseEntity.ok().build();
//    }

    @QueryMapping
    public Page<UserDto> getUsersInPage(@Argument int pageNo, @Argument int pageSize) {
        var sortByCreatedAt = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        return userService.getAll(sortByCreatedAt);
    }

    @SchemaMapping
    public Page<UserDto> getUsersInPageSchema(@Argument int pageNo, @Argument int pageSize) {
        var sortByCreatedAt = PageRequest.of(pageNo, pageSize, Sort.by("createdAt").descending());
        return userService.getAll(sortByCreatedAt);
    }
}
