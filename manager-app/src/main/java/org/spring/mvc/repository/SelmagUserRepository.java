package org.spring.mvc.repository;

import org.spring.mvc.entity.SelmagUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SelmagUserRepository extends CrudRepository<SelmagUser, Integer> {
    Optional<SelmagUser> findByUsername(String username);
}
