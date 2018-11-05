package com.harleycorp.testmybatis;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.harleycorp.service.ISparkUpperService;


@RunWith(SpringJUnit4ClassRunner.class)		//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})

public class TestSpark {

	@SuppressWarnings("unused")
	private static Logger logger=Logger.getLogger(TestSpark.class);
	@Resource 
	private  ISparkUpperService sparkUpperService = null;
	
	@Test
	public void test1(){
		sparkUpperService.upper(null);
	}
	
	
}
