package com.harleycorp.dao;

import com.harleycorp.pojo.LikeUsers;


public interface LikeUsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LikeUsers record);

    int insertSelective(LikeUsers record);

    LikeUsers selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LikeUsers record);

    int updateByPrimaryKey(LikeUsers record);

	LikeUsers selectFirstGZYHByUserid(Integer userid);
}