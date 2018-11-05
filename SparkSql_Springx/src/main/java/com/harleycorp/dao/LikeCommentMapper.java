package com.harleycorp.dao;

import com.harleycorp.pojo.LikeComment;



public interface LikeCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LikeComment record);

    int insertSelective(LikeComment record);

    LikeComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LikeComment record);

    int updateByPrimaryKey(LikeComment record);
    
    int selectDZCountByuserid(Integer userid);
    
    LikeComment selectFirstLikePLUserid(Integer userid);
}