package com.harleycorp.dao;


import java.util.List;

import com.harleycorp.pojo.LikeUsers;
import com.harleycorp.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectBySlugName(User user);
    List<User> selectAll();
    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User record);
    
    LikeUsers selectFirstGZYHByUserid(User record);
}