package com.example.renyibang.dao;

import com.example.renyibang.entity.UserAuth;
import java.util.Optional;

public interface UserAuthDAO {
    Optional<UserAuth> findByUserId(long userId);
    UserAuth save(UserAuth userAuth);
    void deleteByUserId(long userId);
}
