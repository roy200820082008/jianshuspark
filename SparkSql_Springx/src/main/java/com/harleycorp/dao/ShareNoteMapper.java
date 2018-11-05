package com.harleycorp.dao;

import com.harleycorp.pojo.ShareNote;


public interface ShareNoteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShareNote record);

    int insertSelective(ShareNote record);

    ShareNote selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShareNote record);

    int updateByPrimaryKey(ShareNote record);
    
    ShareNote selectFirstNoteByUserid(Integer userid);
}