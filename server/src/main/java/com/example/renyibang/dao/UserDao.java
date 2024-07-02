package com.example.renyibang.dao;

import com.example.renyibang.entity.User;

import java.util.Optional;

public interface UserDao {
  User save(User user);
  void deleteById(long userId);
  Optional<User> findById(long userId);
}
