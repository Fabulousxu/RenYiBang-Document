package com.example.renyibang.repository;

import com.example.renyibang.entity.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    Optional<UserAuth> findByUserId(long userId);
}
