package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> queryUserList();
    List<User> queryUserListByLike(String nameAndId);
    User queryUserById(int id);

    User queryUserByPhone(String phone);

    int addUser(User user);
    int deleteUser(int id);

    int updateUser(User user);
}
