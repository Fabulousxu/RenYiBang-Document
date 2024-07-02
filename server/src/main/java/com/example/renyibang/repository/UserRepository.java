package com.example.renyibang.repository;

import com.example.renyibang.entity.User;
import com.example.renyibang.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByNickname(String nickname);

}
