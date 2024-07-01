package com.example.renyibang.service;

import com.example.renyibang.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;



public interface UserService {
    Optional<User> findById(long userId);
    User save(User user);
    void deleteById(long userId);
    long register(String password, String nickname, String intro, String avatar) throws Exception;
}
