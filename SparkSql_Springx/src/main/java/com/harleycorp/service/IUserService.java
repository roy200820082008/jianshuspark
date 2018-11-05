package com.harleycorp.service;


import java.util.List;

import com.harleycorp.pojo.CommentNotes;
import com.harleycorp.pojo.LikeColls;
import com.harleycorp.pojo.LikeComment;
import com.harleycorp.pojo.LikeNotebooks;
import com.harleycorp.pojo.LikeNotes;
import com.harleycorp.pojo.LikeUsers;
import com.harleycorp.pojo.RewardNotes;
import com.harleycorp.pojo.ShareNote;
import com.harleycorp.pojo.User;

public interface IUserService {
	public User getUserById(int userId);
	public int addUser(User user);
	public void addUserJBXX(User user);
	//爬取用户动态
	public void addUserDT(User user);
	
	public User selectBySlugName(User user);
	
	//查询全部信息，按照id倒序排序
	public List<User> selectAll();
	//查询某个用户的关注专题总数目
	public int selectCountByuserid(Integer userid);
	//查询某个用户的关注文集总数目
	public int selectWJCountByUserid(Integer userid);
	//查询某个用户的关注评论
	public int selectPLCountByUserid (Integer userid);
	//查询某个用户的点赞评论次数
	public int selectDZCountByuserid (Integer userid);
	//查询某个用户的打赏次数
	public int selectDSCountByuserid (Integer userid);
	//查询某个用户的发布的第一篇文章
	public ShareNote selectFirstNoteByUserid(Integer userid);
	//查询某个用户的喜欢的第一篇文章
	public LikeNotes selectFirstLikeNoteByUserid(Integer userid);
	//查询某个用户首次关注的专题
	public LikeColls selectFirstGZZTByUserid(Integer userid);
	//查询某个用户首次关注的用户
	public LikeUsers selectFirstGZYHByUserid(Integer userid);
	//查询某个用户首次关注的文集
	public LikeNotebooks selectFirstLikeWJByUserid (Integer userid);
	//查询某个用户首次的评论
	public CommentNotes selectFirstFBPLByUserid (Integer userid);
	//查询某个用户首次点赞的评论
	public LikeComment selectFirstLikePLUserid (Integer userid);
	//查询某个用户首次打赏
	public RewardNotes selectFirstDSPLUserid (Integer userid);
	//查询某个用户小喜欢文章数目
	public int selectLikeWZCountByuserid (Integer userid);

}
