package com.harleycorp.dao;

import com.harleycorp.pojo.LikeColls;


public interface LikeCollsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LikeColls record);

    int insertSelective(LikeColls record);

    LikeColls selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LikeColls record);

    int updateByPrimaryKey(LikeColls record);
    
    int selectCountByuserid(Integer userid);
    
    LikeColls selectFirstGZZTByUserid(Integer userid);
}