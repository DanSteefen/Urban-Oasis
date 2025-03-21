package com.urban.oasis.urban.oasis_ecommerce.repository;

import com.urban.oasis.urban.oasis_ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
