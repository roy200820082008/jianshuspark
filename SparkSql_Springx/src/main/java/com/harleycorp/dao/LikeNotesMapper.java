package com.harleycorp.dao;

import com.harleycorp.pojo.LikeNotes;


public interface LikeNotesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LikeNotes record);

    int insertSelective(LikeNotes record);

    LikeNotes selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LikeNotes record);

    int updateByPrimaryKey(LikeNotes record);
    
    LikeNotes selectFirstLikeNoteByUserid(Integer userid);
    
    int selectLikeWZCountByuserid(Integer userid);
}