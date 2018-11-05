package com.harleycorp.controler;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.harleycorp.pojo.CateCount;
import com.harleycorp.pojo.CommentNotes;
import com.harleycorp.pojo.DayFreq;
import com.harleycorp.pojo.HourFreq;
import com.harleycorp.pojo.LikeColls;
import com.harleycorp.pojo.LikeComment;
import com.harleycorp.pojo.LikeNotebooks;
import com.harleycorp.pojo.LikeNotes;
import com.harleycorp.pojo.LikeUsers;
import com.harleycorp.pojo.MonthFreq;
import com.harleycorp.pojo.RewardNotes;
import com.harleycorp.pojo.ShareNote;
import com.harleycorp.pojo.User;
import com.harleycorp.service.CommentNoteService;
import com.harleycorp.service.ISparkUpperService;
import com.harleycorp.service.IUserService;
import com.harleycorp.service.RedisCRUD;

@Controller
@RequestMapping("/user")
public class TestController {
	@Resource
	private RedisCRUD redisCRUD;
	@Resource
	private IUserService userService;
	@Resource 
	private CommentNoteService commentNoteService;
	@Resource 
	private  ISparkUpperService sparkUpperService ;
	@ModelAttribute
	public void atfirst(HttpServletRequest request){
		System.out.println("此方法会在此controller每个方法执行前被执行");
	}
	
	
	@RequestMapping("/showUser")
	public ModelAndView toIndex(HttpServletRequest request) throws IOException{
		ModelAndView modelAndView=new ModelAndView();
		
		//slug="8b23f6864f5d";//豆约翰
		String slug=request.getParameter("slug");//用户编码
		//快速出结果模式，不执行爬取信息，直接根据数据库该用户已有信息进行spark运算分析
		//最新结果模式，需要先删掉当前用户在数据库的信息，重新爬取全部信息，然后再执行spark运算分析
		//String moshi=request.getParameter("moshi");//选择的查询模式
		//根据用户输入的用户编码查询数据库user表用户信息，判断当前用户信息是否已经爬取
		User u1=new User();
		u1.setSlug(slug);
		User uu = userService.selectBySlugName(u1);
		if(uu==null){//这个还没有信息被爬取
			//执行信息的爬取
		    //查询当前最后一个用户的id号
			List<User> selectAll = userService.selectAll();
			Integer id =0;
			if(selectAll.size()>0){
				id = userService.selectAll().get(0).getId();//这是当前表中所有用户最大的id号
			}
			//添加新用户
	        uu = new User(id+1,"","","","","","",0,0,0,0,0,0,0);
	        uu.setId(id+1);
	        uu.setSlug(slug);
	        //创建用户（slug、id）
	        userService.addUser(uu);
	        //根据用户编号（slug）爬取用户基本信息
	        userService.addUserJBXX(uu);
	        //爬取用户动态到各个动态表中
	        userService.addUserDT(uu);
		}
		//用户统计信息
		//用户关注专题总数：
		int gzztCount = userService.selectCountByuserid(uu.getId());
		modelAndView.addObject("gzztCount",gzztCount);
		//用户关注文集总数
		int gzwjCount = userService.selectWJCountByUserid(uu.getId());
		modelAndView.addObject("gzwjCount",gzwjCount);
		//用户评论总数
		int plCount = userService.selectPLCountByUserid(uu.getId());
		modelAndView.addObject("plCount",plCount);
		//用户喜欢文章总数
		int xhwzCount = userService.selectLikeWZCountByuserid(uu.getId());
		modelAndView.addObject("xhwzCount",xhwzCount);
		//用户点赞评论总数
		int dzCount = userService.selectDZCountByuserid(uu.getId());
		modelAndView.addObject("dzCount",dzCount);
		//用户打赏评论总数
		int dsCount = userService.selectDSCountByuserid(uu.getId());
		modelAndView.addObject("dsCount",dsCount);
		//某用户发布的第一篇文章
		ShareNote shareNote = userService.selectFirstNoteByUserid(uu.getId());
		modelAndView.addObject("shareNote",shareNote);
		//某用户喜欢的第一篇文章
		LikeNotes likeNotes = userService.selectFirstLikeNoteByUserid(uu.getId());
		modelAndView.addObject("likeNotes",likeNotes);
		//某用户首次关注的专题
		LikeColls likeColls = userService.selectFirstGZZTByUserid(uu.getId());
		modelAndView.addObject("likeColls",likeColls);
		//某用户首次关注的用户
		LikeUsers likeUsers = userService.selectFirstGZYHByUserid(uu.getId());
		modelAndView.addObject("likeUsers",likeUsers);
		//某用户首次关注的文集
		LikeNotebooks likeNotebooks = userService.selectFirstLikeWJByUserid(uu.getId());
		modelAndView.addObject("likeNotebooks",likeNotebooks);
		//某用户首次评论
		CommentNotes commentNotes = userService.selectFirstFBPLByUserid(uu.getId());
		modelAndView.addObject("commentNotes",commentNotes);
		//某用户首次点赞的评论
		LikeComment likeComment = userService.selectFirstLikePLUserid(uu.getId());
		modelAndView.addObject("likeComment",likeComment);
		//某用户首次打赏
		RewardNotes rewardNotes = userService.selectFirstDSPLUserid(uu.getId());
		modelAndView.addObject("rewardNotes",rewardNotes);
		//**********************************以下：执行各个spark计算,返回json字符串
		
		//收集全部评论--词云
		String wordRedisValues = redisCRUD.getRedis(uu.getSlug()+"wordfreq");
		String comment_numRedisValues = redisCRUD.getRedis(uu.getSlug()+"comment_num");
		if(wordRedisValues!=null&&!"".equals(wordRedisValues)&&comment_numRedisValues!=null&&!"".equals(comment_numRedisValues)){//有值
			modelAndView.addObject("wordfreq",wordRedisValues);
			modelAndView.addObject("comment_num",comment_numRedisValues);
		}else{
			List<CommentNotes> list = commentNoteService.selectByPrimaryUserid(uu.getId());
	        modelAndView.addObject("comment_num",list.size());//获得评论数目
	        String cYunJson = sparkUpperService.CYun(uu);
	        modelAndView.addObject("wordfreq",cYunJson);
	        
	        redisCRUD.deleteByKey(uu.getSlug()+"wordfreq");
	        redisCRUD.addKeyAndValue(uu.getSlug()+"wordfreq", cYunJson);
	        redisCRUD.deleteByKey(uu.getSlug()+"comment_num");
	        redisCRUD.addKeyAndValue(uu.getSlug()+"comment_num", String.valueOf(list.size()));
		}
		
        //01.各类动态比例
		//*快速模式下，根据"slug+jsonCateNum"这个键值对去redis查询是否有信息，如果有马上获取
		String redisValues = redisCRUD.getRedis(uu.getSlug()+"jsonCateNum");
		if(redisValues!=null&&!"".equals(redisValues)){//有值
			modelAndView.addObject("jsonCateNum",redisValues);
		}else{//在redis中未能获取到结果
			List<CateCount> cateCountList = sparkUpperService.DTbl(uu);
	        String jsonCateNum = JSON.toJSONString(cateCountList);
	        //*删掉redis中此slug的jsonCateNum的信息，再增加这个slug的jsonCateNum的信息（先删再增）
	        //*如果是快速模式。直接拿slug的jsonCateNum的信息
	        modelAndView.addObject("jsonCateNum",jsonCateNum);
	        //将这个键值对保存到redis
	        redisCRUD.deleteByKey(uu.getSlug()+"jsonCateNum");
	        redisCRUD.addKeyAndValue(uu.getSlug()+"jsonCateNum", jsonCateNum);
		}
		
        //02.每月动态次数
	    String monthsRedisValues = redisCRUD.getRedis(uu.getSlug()+"months");
	    String monthsfreqRedisValues = redisCRUD.getRedis(uu.getSlug()+"monthsfreq");
	    if(monthsRedisValues!=null&&!"".equals(monthsRedisValues)&&monthsfreqRedisValues!=null&&!"".equals(monthsfreqRedisValues)){//有值
	    	modelAndView.addObject("months",monthsRedisValues);
	        modelAndView.addObject("monthsfreq",monthsfreqRedisValues);
	    }else{
	    	MonthFreq monthFreq = sparkUpperService.everyMonthDT(uu);
			String[] arr = monthFreq.getMon().split(",");
	        monthFreq.setMon(JSON.toJSONString(arr));
	        String[] arr2 = monthFreq.getFreq().split(",");
	        monthFreq.setFreq(JSON.toJSONString(arr2));
	        modelAndView.addObject("months",monthFreq.getMon());
	        modelAndView.addObject("monthsfreq",monthFreq.getFreq());
	        
	        redisCRUD.deleteByKey(uu.getSlug()+"months");
	        redisCRUD.addKeyAndValue(uu.getSlug()+"months",monthFreq.getMon());
	        redisCRUD.deleteByKey(uu.getSlug()+"monthsfreq");
	        redisCRUD.addKeyAndValue(uu.getSlug()+"monthsfreq",monthFreq.getFreq());
	    }
		
        //03.每日动态次数
	    String daysRedisValues = redisCRUD.getRedis(uu.getSlug()+"days");
	    String daysfreqRedisValues = redisCRUD.getRedis(uu.getSlug()+"daysfreq");
	    if(daysRedisValues!=null&&!"".equals(daysRedisValues)&&daysfreqRedisValues!=null&&!"".equals(daysfreqRedisValues)){//有值
	    	modelAndView.addObject("days",daysRedisValues);
	        modelAndView.addObject("daysfreq",daysfreqRedisValues);
	    }else{
	    	DayFreq dayFreq = sparkUpperService.everyDayDT(uu);
	        String[] arrDay = dayFreq.getDay().split(",");
	        dayFreq.setDay(JSON.toJSONString(arrDay));
	        String[] arrDay2 = dayFreq.getFreq().split(",");
	        dayFreq.setFreq(JSON.toJSONString(arrDay2));
	        modelAndView.addObject("days",dayFreq.getDay());
	        modelAndView.addObject("daysfreq",dayFreq.getFreq());
	        
	        redisCRUD.deleteByKey(uu.getSlug()+"days");
	        redisCRUD.addKeyAndValue(uu.getSlug()+"days",dayFreq.getDay());
	        redisCRUD.deleteByKey(uu.getSlug()+"daysfreq");
	        redisCRUD.addKeyAndValue(uu.getSlug()+"daysfreq",dayFreq.getFreq());
	    }

        
        //04.每天各时间点动态次数
	    String hoursRedisValues = redisCRUD.getRedis(uu.getSlug()+"hours");
	    String hoursfreqRedisValues = redisCRUD.getRedis(uu.getSlug()+"hoursfreq");
	    if(hoursRedisValues!=null&&!"".equals(hoursRedisValues)&&hoursfreqRedisValues!=null&&!"".equals(hoursfreqRedisValues)){//有值
	    	modelAndView.addObject("hours",hoursRedisValues);
	        modelAndView.addObject("hoursfreq",hoursfreqRedisValues);
	    }else{
	    	HourFreq hourFreq = sparkUpperService.getHourFreqByUserID(uu);
	        String[] arrHour = hourFreq.getTime().split(",");
	        hourFreq.setTime(JSON.toJSONString(arrHour));
	        String[] arrHour2 = hourFreq.getFreq().split(",");
	        hourFreq.setFreq(JSON.toJSONString(arrHour2));
	        modelAndView.addObject("hours",hourFreq.getTime());
	        modelAndView.addObject("hoursfreq",hourFreq.getFreq());
	        
	        redisCRUD.deleteByKey(uu.getSlug()+"hours");
	        redisCRUD.addKeyAndValue(uu.getSlug()+"hours",hourFreq.getTime());
	        redisCRUD.deleteByKey(uu.getSlug()+"hoursfreq");
	        redisCRUD.addKeyAndValue(uu.getSlug()+"hoursfreq",hourFreq.getFreq());
	    }

	    //05.每周动态次数（2.3版本以前的sparkSql不支持dayofweek函数）
        /*
        WeekFreq weekFreq = sparkUpperService.getWeekFreqByUserID(user);
        String[] arrWeek = weekFreq.getTime().split(",");
        weekFreq.setTime(JSON.toJSONString(arrWeek));
        String[] arrWeek2 = weekFreq.getFreq().split(",");
        weekFreq.setFreq(JSON.toJSONString(arrWeek2));
        modelAndView.addObject("weeks",weekFreq.getTime());
        modelAndView.addObject("weeksfreq",weekFreq.getFreq());
        */
        
        User u = userService.getUserById(uu.getId());
        modelAndView.addObject("user",u);
        modelAndView.setViewName("timeline");//显示页
        return modelAndView;
	}
}