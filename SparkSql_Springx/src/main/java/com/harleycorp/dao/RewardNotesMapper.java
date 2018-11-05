package com.harleycorp.dao;

import com.harleycorp.pojo.RewardNotes;


public interface RewardNotesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RewardNotes record);

    int insertSelective(RewardNotes record);

    RewardNotes selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RewardNotes record);

    int updateByPrimaryKey(RewardNotes record);
    
    int selectDSCountByuserid(Integer userid);
    
    RewardNotes selectFirstDSPLUserid (Integer userid);
}