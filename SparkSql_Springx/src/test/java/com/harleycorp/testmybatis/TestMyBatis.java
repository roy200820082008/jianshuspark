package com.harleycorp.testmybatis;

import java.io.IOException;


import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



import com.harleycorp.pojo.User;
import com.harleycorp.service.CommentNoteService;
import com.harleycorp.service.ISparkUpperService;
import com.harleycorp.service.IUserService;
import com.harleycorp.service.RedisCRUD;

@RunWith(SpringJUnit4ClassRunner.class)
// 表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = { "classpath:spring-mybatis.xml" })
public class TestMyBatis {

	// private static Logger logger=Logger.getLogger(TestMyBatis.class);
	@Resource
	private IUserService userService ;
	@Resource
	private CommentNoteService commentNoteService;
	@Resource
	private ISparkUpperService iSparkUpperService;
	@Resource
	private RedisCRUD redisCRUD;
	@Test
	public void test1() throws IOException {
		List<User> selectAll = userService.selectAll();
		System.out.println(selectAll);
		//int i = userService.selectCountByuserid(11);
		//System.out.println(i);
		
		
	}

}
