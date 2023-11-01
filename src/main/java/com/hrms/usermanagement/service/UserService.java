package com.hrms.usermanagement.service;

import com.hrms.global.mapper.HrmsMapper;
import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.model.Role;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.model.UserRole;
import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.repository.UserRoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HrmsMapper modelMapper;

    private Boolean checkUserExist(String username) throws Exception {
        if (userRepository.existsByUsername(username)) {
            throw new Exception("User already exists");
        }
        return true;
    }

    public Page<UserDto> searchUsers(String search, List<Integer> roleIds, Boolean status, Pageable pageable) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Specification<User> filters = Specification
                .<User>where((status == null) ? null : (r, q, c) -> c.equal(r.get("isEnabled"), status))
                .and((search == null) ? null : (r, q, c) -> c.like(r.get("username"), "%" + search + "%"))
                .and((roleIds.isEmpty()) ? null : (r, q, c) -> r.get("userRoles").get("role").get("roleId").in(roleIds));

        query = query.where(filters.toPredicate(root, query, builder))
                .multiselect(root.get("userId"), root.get("username"), root.get("isEnabled"), root.get("createdAt"))
                ;

        var users = userRepository.findAll((com.querydsl.core.types.Predicate) filters.toPredicate(root, query, builder), pageable);
        return users.map(user -> modelMapper.map(user, UserDto.class));
    }

    public List<Role> getRoles(Integer userId) {
        Specification<UserRole> spec = (root, query, builder) -> builder.equal(root.get("user").get("userId"), userId);
        return userRoleRepository.findAllByUserId(userId);
    }

    //TODO: RETURN USER DTO NOT INCLUDING PASSWORD
    public UserDto getUser(Integer userId) throws Exception {
        Specification<User> spec = ((root, query, builder) -> builder.equal(root.get("userId"),userId));
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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
        CriteriaUpdate<User> update = criteriaUpdate.set("isEnabled", status);

        Root<User> root = update.from(User.class);
        update.where(root.get("userId").in(userIds));

        entityManager.createQuery(update).executeUpdate();
    }

    private UserDto getUserDto(Integer userId) throws Exception {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserDto> query = cb.createQuery(UserDto.class);
        Root<User> root = query.from(User.class);

        query.multiselect(root.get("userId"), root.get("username"), root.get("isEnabled"), root.get("createdAt"));
        query.where(cb.equal(root.get("userId"), userId));

        return entityManager.createQuery(query).getSingleResult();
    }
    public List<UserDto> getUsers(Integer userId) throws Exception {
        Specification<User> spec = ((root, query, builder) -> builder.greaterThan(root.get("userId"), userId));
        return userRepository.findAll(spec).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }


}