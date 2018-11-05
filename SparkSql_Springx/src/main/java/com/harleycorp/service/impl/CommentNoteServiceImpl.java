package com.harleycorp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.harleycorp.dao.CommentNotesMapper;
import com.harleycorp.dao.LikeCollsMapper;
import com.harleycorp.dao.LikeCommentMapper;
import com.harleycorp.dao.LikeNotebooksMapper;
import com.harleycorp.dao.LikeNotesMapper;
import com.harleycorp.dao.LikeUsersMapper;
import com.harleycorp.dao.RewardNotesMapper;
import com.harleycorp.dao.ShareNoteMapper;
import com.harleycorp.dao.UserMapper;
import com.harleycorp.pojo.CommentNotes;
import com.harleycorp.service.CommentNoteService;




@Service
public class CommentNoteServiceImpl  implements CommentNoteService{
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private LikeCommentMapper likeCommentMapper;
	@Resource
	private CommentNotesMapper commentNotesMapper;
	@Resource
	private LikeNotesMapper likeNotesMapper;
	@Resource
	private RewardNotesMapper rewardNotesMapper;
	@Resource
	private ShareNoteMapper shareNoteMapper;
	@Resource
	private LikeUsersMapper likeUsersMapper;
	@Resource
	private LikeCollsMapper likeCollsMapper;
	@Resource
	private LikeNotebooksMapper likeNotebooksMapper;
	@Override
	public List<CommentNotes> selectByPrimaryUserid(int userid) {
		return commentNotesMapper.selectByPrimaryUserid(userid);
	}
    

}
