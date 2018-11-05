package com.harleycorp.service.impl;

import java.io.IOException;
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
import com.harleycorp.pojo.LikeColls;
import com.harleycorp.pojo.LikeComment;
import com.harleycorp.pojo.LikeNotebooks;
import com.harleycorp.pojo.LikeNotes;
import com.harleycorp.pojo.LikeUsers;
import com.harleycorp.pojo.RewardNotes;
import com.harleycorp.pojo.ShareNote;
import com.harleycorp.pojo.User;
import com.harleycorp.service.IUserService;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@Service("userService")
public class UserServiceImpl  implements IUserService{
	
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
    //根据userid查询用户对象
	public User getUserById(int userId) {
		return this.userMapper.selectByPrimaryKey(userId);
	}
	//根据slug查询用户对象
	public User selectBySlugName(User user) {
		return this.userMapper.selectBySlugName(user);
	}
	
	//查询全部信息，按照id倒序排序
	public List<User> selectAll() {
		return this.userMapper.selectAll();
	}

	//增加用户
	public int addUser(User user) {
		int insert = userMapper.insert(user);
		return insert;
	}
	//查询某一个用户关注专题总数
	public int selectCountByuserid(Integer userid) {
		return likeCollsMapper.selectCountByuserid(userid);
	}
	//查询某一个用户关注文集总数
	public int selectWJCountByUserid(Integer userid) {
		return likeNotebooksMapper.selectCountByUserid(userid);
	}
	//查询某一个用户评论总数
	public int selectPLCountByUserid(Integer userid) {
		return commentNotesMapper.selectPLCountByuserid(userid);
	}
	//查询某一个用户点赞评论数目
	public int selectDZCountByuserid(Integer userid) {
		return likeCommentMapper.selectDZCountByuserid(userid);
	}
	//查询某一个用户打赏数目
	public int selectDSCountByuserid(Integer userid) {
		return rewardNotesMapper.selectDSCountByuserid(userid);
	}
	//jsoup--根据用户编号（slug）爬取用户基本信息
	@Override
	public void addUserJBXX(User user) {
		Connection connect = Jsoup.connect("https://www.jianshu.com/users/"+user.getSlug()+"/followers");

        // 得到Document对象
        Document document;
		try {
			document = connect.get();
			//System.out.println(document.toString());
	        Elements elements = document.select(".avatar img");
	        String strHead = elements.attr("abs:src");
	        String[] Head = strHead.split("[?]");//截取链接问号前的是头像图片地址
	        elements = document.select(".title .name");
	        String name = elements.text().replaceAll("[^\\u0000-\\uFFFF]", "");
            elements = document.select(".info li");
	        //关注人数
	        int follow = Integer.parseInt(elements.get(0).select("p").text());
	        //粉丝人数
	        int followers = Integer.parseInt(elements.get(1).select("p").text());
	        //发表文章数
	        int article_num = Integer.parseInt(elements.get(2).select("p").text());
	        //文字数
	        int words_num = Integer.parseInt(elements.get(3).select("p").text());
	        //收获喜欢数
	        int love_num = Integer.parseInt(elements.get(4).select("p").text());
            user.setArticlesNum(article_num);//发表文章数
	        user.setHeadPic(Head[0]);//头像链接
	        user.setNickname(name);//用户昵称
	        user.setBeLikedNum(love_num);//收获喜欢数
	        user.setFollowersNum(followers);//粉丝人数
	        user.setFollowingNum(follow);//关注人数
	        user.setWordsNum(words_num);//文字数
	        //根据用户id更新用户表
	        //System.out.println(user.getId());
	        userMapper.updateByPrimaryKey(user);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
     }

	//爬取用户各类的动态，保存到各类动态表中
	@Override
	public void addUserDT(User user) {
		int maxid=0;
		int pageno=1;
		for(pageno=1;pageno<1000;pageno++){//设置抓去1000页动态
			String query = "?max_id="+maxid+"&page="+pageno;
	        if(maxid == 0)
	        {
	            query = "";
	        }
	        Connection connect = Jsoup.connect("http://www.jianshu.com/users/"+user.getSlug()+"/timeline"+query);
	        String sss="http://www.jianshu.com/users/"+user.getSlug()+"/timeline"+query;
	        System.out.println("执行爬取动态："+sss);
	        try {
	            // 得到Document对象
	            Document document = connect.get();

	            Elements elements2 = document.select(".note-list li");
	            if(elements2.last() == null) {
	                return;
	            }
	            String id = elements2.last().id();
	            //这个值是翻页除了page的另一个参数，第二页的值在第一页、第三页的值在第二页
	            //将这个值获取到，传给下一个循环即可
	            maxid = Integer.parseInt(id.split("-")[1]) - 1;
	            for(Element element : elements2)
	            {

	            	//System.out.println(element.toString());
	                Elements elements3  = element.select("span[data-type=like_comment]");
	                if(elements3.size() > 0)
	                {
	                	//System.out.println("喜欢了评论");
	                	LikeComment likeComment =  new LikeComment();
	                    likeComment.setCommentContent(element.getElementsByClass("comment").text().replaceAll("[^\\u0000-\\uFFFF]", ""));
	                    likeComment.setUserid(user.getId());
	                    likeComment.setTime(elements3.attr("data-datetime"));
	                    likeCommentMapper.insertSelective(likeComment);
	                }

	                elements3  = element.select("span[data-type=comment_note]");
	                if(elements3.size() > 0)
	                {
	                    //System.out.println("发表评论");
	                    //System.out.println("评论内容："+element.select(".comment").first().ownText());
	                    //System.out.println("文章编号："+element.select("a.title").first().attr("href").replace("/p/",""));
	                    //System.out.println(elements3.attr("data-datetime"));
	                	CommentNotes commentNotes =  new CommentNotes();
	                    commentNotes.setUserid(user.getId());
	                    String ownText = element.select(".comment").first().ownText().toString().replaceAll("[^\\u0000-\\uFFFF]", "");
	                    //调用工具类过滤掉评论中的表情
	                    //ownText = FilterBiaoQing.filterEmoji(ownText);
	                    commentNotes.setCommentText(ownText);//只保存不包含表情的评论
	                    commentNotes.setNoteId(element.select("a.title").first().attr("href").replace("/p/",""));
	                    commentNotes.setTime(elements3.attr("data-datetime"));
	                    commentNotesMapper.insertSelective(commentNotes);
	                 }
	                elements3  = element.select("span[data-type=like_note]");
	                if(elements3.size() > 0)
	                {
	                	//System.out.println("喜欢文章");
	                	//System.out.println(element.toString());
	                    LikeNotes likeNotes =  new LikeNotes();
	                    likeNotes.setSlug(element.getElementsByClass("origin-author").select("a").attr("href").replace("/users/",""));
	                    likeNotes.setCommentText(element.getElementsByClass("title").text().replaceAll("[^\\u0000-\\uFFFF]", ""));
	                    likeNotes.setNoteId(element.select("a.title").attr("href").toString().replace("/p/",""));
	                    likeNotes.setUserid(user.getId());
	                    likeNotes.setTime(elements3.attr("data-datetime"));
	                    likeNotesMapper.insertSelective(likeNotes);
	                }
	                elements3  = element.select("span[data-type=reward_note]");
	                if(elements3.size() > 0)
	                {
	                	//System.out.println("赞赏文章");
	                    RewardNotes rewardNotes =  new RewardNotes();
	                    rewardNotes.setUserid(user.getId());
	                    rewardNotes.setNoteId(element.getElementsByClass("title").attr("href").toString().replaceAll("[^\\u0000-\\uFFFF]", ""));
	                    rewardNotes.setTime(elements3.attr("data-datetime"));
	                    rewardNotesMapper.insertSelective(rewardNotes);
	                    
	                }

	                elements3  = element.select("span[data-type=share_note]");
	                if(elements3.size() > 0)
	                {
	                	//System.out.println("发表文章");
	                    //System.out.println(element1.attr("href").replace("/p/",""));
	                    //System.out.println(elements3.attr("data-datetime"));
	                    Element element1 = elements3.get(0).parent().parent().nextElementSibling();
	                    ShareNote shareNote =  new ShareNote();
	                    shareNote.setUserid(user.getId());
	                    shareNote.setNoteId(element1.attr("href").replace("/p/","").replaceAll("[^\\u0000-\\uFFFF]", ""));
	                    shareNote.setTime(elements3.attr("data-datetime"));
	                    shareNoteMapper.insertSelective(shareNote);
	                    
	                }
	                elements3  = element.select("span[data-type=like_user]");
	                if(elements3.size() > 0)
	                {
	                	//System.out.println("关注作者");
	                	LikeUsers likeUsers =  new LikeUsers();
	                    likeUsers.setUserid(user.getId());
	                    likeUsers.setSlug(element.getElementsByClass("title").attr("href").toString().replaceAll("[^\\u0000-\\uFFFF]", ""));
	                    likeUsers.setTime(elements3.attr("data-datetime"));
	                    likeUsersMapper.insertSelective(likeUsers);
	                    
	                }
	                elements3  = element.select("span[data-type=like_collection]");
	                if(elements3.size() > 0)
	                {
	                	//System.out.println("关注专题");
	                	LikeColls likeColls =  new LikeColls();
	                    likeColls.setUserid(user.getId());
	                    likeColls.setCollId(element.getElementsByClass("title").attr("href").toString().replaceAll("[^\\u0000-\\uFFFF]", ""));
	                    likeColls.setTime(elements3.attr("data-datetime"));
	                    likeCollsMapper.insertSelective(likeColls);
	                    
	                }
	                elements3  = element.select("span[data-type=like_notebook]");
	                if(elements3.size() > 0)
	                {
	                	//System.out.println("关注文集");
	                    LikeNotebooks likeNotebooks =  new LikeNotebooks();
	                    likeNotebooks.setUserid(user.getId());
	                    likeNotebooks.setNotebookId(element.getElementsByClass("title").attr("href").toString().replaceAll("[^\\u0000-\\uFFFF]", ""));
	                    likeNotebooks.setTime(elements3.attr("data-datetime"));
	                    likeNotebooksMapper.insertSelective(likeNotebooks);
	                    
	                }
	                //第一次加入简书时间
	                elements3  = element.select("span[data-type=join_jianshu]");
	                if(elements3.size() > 0){
	                	String text = elements3.attr("data-datetime");//加入简书时间
	                	if(text.length()>=10){
	                		String joinDate = text.substring(0,10);
	                		//根据当前用户id，把这个值更新到user表的joinTime列中
	                		user.setJoinTime(joinDate);
	                		userMapper.updateByPrimaryKeySelective(user);
	                	}
	                }
	             }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		
	}
	//某用户发布的第一篇文章
	public ShareNote selectFirstNoteByUserid(Integer userid) {
		return shareNoteMapper.selectFirstNoteByUserid(userid);
	}
	@Override
	public LikeNotes selectFirstLikeNoteByUserid(Integer userid) {
		return likeNotesMapper.selectFirstLikeNoteByUserid(userid);
	}
	@Override
	public LikeColls selectFirstGZZTByUserid(Integer userid) {
		return likeCollsMapper.selectFirstGZZTByUserid(userid);
	}
	@Override
	public LikeUsers selectFirstGZYHByUserid(Integer userid) {
		return likeUsersMapper.selectFirstGZYHByUserid(userid);
	}
	@Override
	public LikeNotebooks selectFirstLikeWJByUserid(Integer userid) {
		return likeNotebooksMapper.selectFirstLikeWJByUserid(userid);
	}
	@Override
	public CommentNotes selectFirstFBPLByUserid(Integer userid) {
		return commentNotesMapper.selectFirstFBPLByUserid(userid);
	}
	@Override
	public LikeComment selectFirstLikePLUserid(Integer userid) {
		return likeCommentMapper.selectFirstLikePLUserid(userid);
	}
	@Override
	public RewardNotes selectFirstDSPLUserid(Integer userid) {
		return rewardNotesMapper.selectFirstDSPLUserid(userid);
	}
	@Override
	public int selectLikeWZCountByuserid(Integer userid) {
		return likeNotesMapper.selectLikeWZCountByuserid(userid);
	}
}
