package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.User;

import java.util.List;

public interface IUserService {
    List<User> queryUserList();
    List<User> queryUserListByLike(String nameAndId);
    int deleteUser(int id);
    User queryUserById(int id);
    User queryUserByPhone(String Phone);
    int updateUser(User user);
    int addUser(User user);
}
