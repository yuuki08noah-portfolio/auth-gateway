package com.auth.user.repository;

import com.auth.user.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User getUserById(String id);
  User getUserByEmail(String email);
}
