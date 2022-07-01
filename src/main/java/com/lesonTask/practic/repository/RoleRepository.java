package com.lesonTask.practic.repository;

import java.util.Optional;

import com.lesonTask.practic.model.ERole;
import com.lesonTask.practic.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);


}