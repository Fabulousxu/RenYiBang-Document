package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.User;
import com.example.renyibang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findById(long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public String updateUserByUserId(long userId, String nickname, String avatar, String intro)
    {
        try
        {
            User user = userRepository.findById(userId).orElse(null);
            if(user == null)
            {
                return "用户不存在！";
            }

            user.setNickname(nickname);
            user.setAvatar(avatar);
            user.setIntro(intro);

            userRepository.save(user);

            return "修改用户信息成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
