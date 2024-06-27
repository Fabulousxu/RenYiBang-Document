package com.example.renyibang.dao;

import com.example.renyibang.entity.User;

public interface UserDao {
  User findById(long userId);
}
