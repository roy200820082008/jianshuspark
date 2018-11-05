/**
 * 
 */
package com.harleycorp.service;

import java.io.IOException;
import java.util.List;

import com.harleycorp.pojo.CateCount;
import com.harleycorp.pojo.DayFreq;
import com.harleycorp.pojo.HourFreq;
import com.harleycorp.pojo.MonthFreq;
import com.harleycorp.pojo.User;
import com.harleycorp.pojo.WeekFreq;


/**
 * @author kevin
 *
 */
//spark计算接口类
public interface ISparkUpperService {
	//测试用例
	public String upper(String inputFilee);
	//01.各类动态比例
	public List<CateCount> DTbl(User user);
	//02.每月动态次数
	public MonthFreq everyMonthDT(User user);
	//03.每日动态次数
	public DayFreq everyDayDT(User user);
	//04.每天各时间点动态次数
	public HourFreq getHourFreqByUserID(User user);
	//05.每周动态次数
	public WeekFreq getWeekFreqByUserID(User user);
	//评论词云
	public String  CYun(User user) throws IOException;
	
	
}
