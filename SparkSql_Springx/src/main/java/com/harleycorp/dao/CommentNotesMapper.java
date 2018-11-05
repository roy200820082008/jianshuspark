package com.harleycorp.dao;

import java.util.List;

import com.harleycorp.pojo.CommentNotes;


public interface CommentNotesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CommentNotes record);

    int insertSelective(CommentNotes record);

    List<CommentNotes> selectByPrimaryUserid(Integer userid);

    int updateByPrimaryKeySelective(CommentNotes record);

    int updateByPrimaryKey(CommentNotes record);
    
    int selectPLCountByuserid(Integer userid);
    
    CommentNotes selectFirstFBPLByUserid(Integer userid);
}