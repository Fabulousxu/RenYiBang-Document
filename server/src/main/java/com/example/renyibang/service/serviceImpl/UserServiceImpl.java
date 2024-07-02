package com.example.renyibang.service.serviceImpl;


import com.example.renyibang.dao.UserAuthDAO;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.User;
import com.example.renyibang.entity.UserAuth;
import com.example.renyibang.repository.UserRepository;
import com.example.renyibang.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDAO;

    @Autowired
    private UserAuthDAO userAuthDAO;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findById(long userId) {
        return userDAO.findById(userId);
    }

    @Override
    public User save(User user) {
        return userDAO.save(user);
    }

    @Override
    public void deleteById(long userId) {
        userDAO.deleteById(userId);
    }
    @Override
    public long register(String password, String nickname, String intro, String avatar) throws Exception {
        System.out.println("1");
        User user = new User();
        user.setNickname(nickname);
        user.setIntro(intro);
        user.setAvatar(avatar);
        userRepository.save(user);
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(user.getUserId());
        userAuth.setPassword(password);
        userAuthDAO.save(userAuth);

        System.out.println("3");
        return user.getUserId();
    }
}
