package com.hrms.usermanagement.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CustomRepository<T> extends JpaRepository<T, Integer> {

    <R> List<R> findAllBy(Specification<T> spec, Class<R> type);
}
