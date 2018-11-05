package com.harleycorp.dao;

import com.harleycorp.pojo.LikeNotebooks;


public interface LikeNotebooksMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LikeNotebooks record);

    int insertSelective(LikeNotebooks record);

    LikeNotebooks selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LikeNotebooks record);

    int updateByPrimaryKey(LikeNotebooks record);
    
    int selectCountByUserid (Integer userid);
    
    LikeNotebooks selectFirstLikeWJByUserid (Integer userid);
}