package com.hrms.usermanagement.service;

import com.hrms.global.mapper.HrmsMapper;
import com.hrms.global.paging.Pagination;
import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.dto.UserDtoPagination;
import com.hrms.usermanagement.model.QUser;
import com.hrms.usermanagement.model.Role;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.model.UserRole;
import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.repository.UserRoleRepository;
import com.hrms.usermanagement.specification.UserSpecification;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @PersistenceContext
    EntityManager entityManager;

    @jakarta.persistence.PersistenceContext
    jakarta.persistence.EntityManager em;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserSpecification userSpecification;

    @Autowired
    private HrmsMapper modelMapper;

    public UserService() {
    }

    private Boolean checkUserExist(String username) throws Exception {
        if (userRepository.existsByUsername(username)) {
            throw new Exception("User already exists");
        }
        return true;
    }

    public UserDtoPagination searchUsers(@Nullable String search,
                                         @Nullable List<Integer> roleIds,
                                         @Nullable Boolean status,
                                         Pageable pageable) {
        Specification<User> spec = userSpecification.getUsersSpec(search, roleIds, status);

        Page<User> users = userRepository.findAll(spec, pageable);

        Pagination pagination = new Pagination(pageable.getPageNumber() + 1, pageable.getPageSize(),
                users.getTotalElements(),
                users.getTotalPages()
        );

        return new UserDtoPagination(users.map(u -> modelMapper.map(u, UserDto.class)), pagination, users.getTotalElements());
    }

    public List<Role> getRoles(Integer userId) {
        Specification<UserRole> spec = (root, query, builder) -> builder.equal(root.get("user").get("userId"), userId);
        return userRoleRepository.findAllByUserId(userId);
    }

    public UserDto getUser(Integer userId) throws Exception {
        return modelMapper.map(userRepository
                .findById(userId)
                .orElseThrow(() -> new Exception("User Not Exist")), UserDto.class);
    }

    @Transactional
    public User createUser(SignupDto signupDto) throws Exception {
        checkUserExist(signupDto.getUsername());

        var user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setIsEnabled(false);
        user.setCreatedAt(Date.valueOf(LocalDate.now()));

        return userRepository.save(user);
    }

    @Transactional
    public Boolean updateUsers(List<Integer> userIds, Boolean status, List<Integer> roleIds) {
        deleteUserRolesNotInRoles(userIds, roleIds);
        List<Pair<Integer, Integer>> userRolePairItems = getUserRolePairItems(userIds, roleIds);
        insertUserRolePairs(userRolePairItems);
        updateUsersStatus(userIds, status);
        return true;
    }

    private void deleteUserRolesNotInRoles(List<Integer> userIds, List<Integer> roleIds) {
        Specification<UserRole> deleteNotInRoles = (root, query, criteriaBuilder) -> {
            Predicate roleIdNotInPredicate = root.get("role").get("roleId").in(roleIds).not();
            return criteriaBuilder.and(getEqualUserIdsPredicate(userIds, root), roleIdNotInPredicate);
        };
        userRoleRepository.delete(deleteNotInRoles);
    }

    private List<Pair<Integer, Integer>> getUserRolePairItems(List<Integer> userIds, List<Integer> roleIds) {
        return userIds.stream()
                .flatMap(userId -> roleIds.stream().map(roleId -> Pair.of(userId, roleId)))
                .toList();
    }

    private void insertUserRolePairs(List<Pair<Integer, Integer>> userRolePairItems) {
        userRolePairItems.stream()
                .filter(pair -> userRolePairItems.stream().noneMatch(existingPair -> existingPair.equals(pair)))
                .forEach(pair -> userRoleRepository.addRoleIdUserId(pair.getFirst(), pair.getSecond()));
    }

    static Predicate getEqualUserIdsPredicate(List<Integer> userIds, Root<UserRole> root) {
        return root.get("user").get("userId").in(userIds);
    }

    private void updateUsersStatus(List<Integer> userIds, boolean status) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
        CriteriaUpdate<User> update = criteriaUpdate.set("isEnabled", status);

        Root<User> root = update.from(User.class);
        update.where(root.get("userId").in(userIds));

        em.createQuery(update).executeUpdate();
    }

    //    private UserDto getUserDto(Integer userId) throws Exception {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<UserDto> query = cb.createQuery(UserDto.class);
//        Root<User> root = query.from(User.class);
//
//        query.multiselect(root.get("userId"), root.get("username"), root.get("isEnabled"), root.get("createdAt"));
//        query.where(cb.equal(root.get("userId"), userId));
//
//        return entityManager.createQuery(query).getSingleResult();
//    }

    //CODE TO DISCUSS WITH MR.DAO ABOUT SELECT SPECIFIC COLUMNS + PAGEABLE + SPECIFICATION --> NOT WORKING
    // Solution: Custom?
    public List<UserDto> getUsers(Integer userId) throws Exception {
        Specification<User> spec = ((root, query, builder) -> builder.greaterThan(root.get("userId"), userId));
        return userRepository.findAll(spec).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }


}