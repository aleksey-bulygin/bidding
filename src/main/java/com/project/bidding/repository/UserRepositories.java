package com.project.bidding.repository;

import com.project.bidding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositories extends JpaRepository<User, Long > {
    User findByUsername(String username);
}
