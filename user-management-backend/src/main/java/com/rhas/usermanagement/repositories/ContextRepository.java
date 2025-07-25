package com.rhas.usermanagement.repositories;

import com.rhas.usermanagement.entities.Context;
import com.rhas.usermanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContextRepository extends JpaRepository<Context, Long> {
    Optional<Context> findByName(String name);
    Optional<Context> findById(Long id);
}
