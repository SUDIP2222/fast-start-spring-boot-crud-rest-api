package com.crud.faststartspringbootcrudrestapi.repository;

import com.crud.faststartspringbootcrudrestapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT (u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email=?1")
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email=?1")
    User findByEmail(String email);
}
