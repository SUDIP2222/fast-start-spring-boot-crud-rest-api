package com.crud.faststartspringbootcrudrestapi.repository;

import com.crud.faststartspringbootcrudrestapi.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    @Query("SELECT u FROM Role u WHERE u.name=?1")
    Role findByName(String name);
}
