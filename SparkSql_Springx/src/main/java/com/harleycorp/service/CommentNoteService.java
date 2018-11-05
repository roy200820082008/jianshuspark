package com.harleycorp.service;

import java.util.List;

import com.harleycorp.pojo.CommentNotes;



public interface CommentNoteService {
	//查询某个用户的全部评论
	public List<CommentNotes> selectByPrimaryUserid(int userid);
}
