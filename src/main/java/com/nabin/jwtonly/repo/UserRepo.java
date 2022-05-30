package com.nabin.jwtonly.repo;

import com.nabin.jwtonly.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
}
