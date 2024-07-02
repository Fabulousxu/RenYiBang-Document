package com.example.renyibang.service;

import com.example.renyibang.entity.UserAuth;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface UserAuthService {
    Optional<UserAuth> findByUserId(long userId);
    UserAuth save(UserAuth userAuth);
    void deleteByUserId(long userId);
}
