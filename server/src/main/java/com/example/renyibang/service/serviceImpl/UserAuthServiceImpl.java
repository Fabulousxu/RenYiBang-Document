package com.example.renyibang.service.serviceImpl;

import com.example.renyibang.dao.UserAuthDAO;
import com.example.renyibang.entity.UserAuth;
import com.example.renyibang.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserAuthDAO userAuthDAO;

    @Override
    public Optional<UserAuth> findByUserId(long userId) {
        return userAuthDAO.findByUserId(userId);
    }

    @Override
    public UserAuth save(UserAuth userAuth) {
        return userAuthDAO.save(userAuth);
    }

    @Override
    public void deleteByUserId(long userId) {
        userAuthDAO.deleteByUserId(userId);
    }
}
