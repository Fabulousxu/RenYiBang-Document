package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.User;
import com.example.renyibang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

}
