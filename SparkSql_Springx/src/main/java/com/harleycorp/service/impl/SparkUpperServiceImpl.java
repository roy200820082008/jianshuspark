/**
 * 
 */
package com.harleycorp.service.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.apache.spark.sql.Row;

import javax.annotation.Resource;

import org.apache.spark.sql.Dataset;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.springframework.stereotype.Service;
import org.apache.spark.sql.DataFrameReader;

import com.alibaba.fastjson.JSON;
import com.harleycorp.pojo.CateCount;
import com.harleycorp.pojo.CommentNotes;
import com.harleycorp.pojo.DayFreq;
import com.harleycorp.pojo.HourFreq;
import com.harleycorp.pojo.MonthFreq;
import com.harleycorp.pojo.User;
import com.harleycorp.pojo.WeekFreq;
import com.harleycorp.service.CommentNoteService;
import com.harleycorp.service.ISparkUpperService;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.SparkSession.Builder;

import java.io.StringReader;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * @author kevin
 * 
 */
@Service
public class SparkUpperServiceImpl implements ISparkUpperService, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7499410536543484376L;
	@Resource
	private CommentNoteService commentNoteService;

	public String url = "jdbc:mysql://192.168.68.244:3306/myjianshu?useUnicode=true&characterEncoding=UTF-8";
	public String username = "root";
	public String password = "root";

	@Resource
	private SparkConf sparkConf;

	public String upper(String inputFilee) {
		String table = "testtable";
		Logger logger = Logger.getLogger(SparkUpperServiceImpl.class);
		logger.info("=======================this url:" + this.url);
		logger.info("=======================this table:" + table);
		logger.info("=======================this username:" + this.username);
		logger.info("=======================this password:" + this.password);
		Builder builder = SparkSession.builder().config(sparkConf);
		SparkSession sparkSession = builder.getOrCreate();// 单例模式创建一个SparkSession
		DataFrameReader reader = sparkSession.read().format("jdbc");
		reader.option("url", url);// 数据库路径
		reader.option("dbtable", table);// 数据表名
		reader.option("driver", "com.mysql.jdbc.Driver");
		reader.option("user", username);
		reader.option("password", password);

		Dataset<Row> p = reader.load();
		p.createOrReplaceTempView(table);
		// 执行sql查询
		Dataset<Row> dataset = sparkSession.sql("select * from " + table);
		dataset.show();
		String zd = dataset.javaRDD().collect().toString();
		sparkSession.close();

		return zd;
	}

	// 01.各类动态比例
	@Override
	public List<CateCount> DTbl(User user) {
		
		List<CateCount> list = new ArrayList<CateCount>();
		String table1 = "comment_notes";
		Builder builder = SparkSession.builder().config(sparkConf);
		SparkSession sparkSession = builder.getOrCreate();// 单例模式创建一个SparkSession
		DataFrameReader reader = sparkSession.read().format("jdbc");
		reader.option("url", url);// 数据库路径
		reader.option("dbtable", table1);// 数据表名
		reader.option("driver", "com.mysql.jdbc.Driver");
		reader.option("user", username);
		reader.option("password", password);
		Dataset<Row> p = reader.load();
		p.createOrReplaceTempView("comment_notes");

		String table2 = "like_notes";
		DataFrameReader reader2 = sparkSession.read().format("jdbc");
		reader2.option("url", url);// 数据库路径
		reader2.option("dbtable", table2);// 数据表名
		reader2.option("driver", "com.mysql.jdbc.Driver");
		reader2.option("user", username);
		reader2.option("password", password);
		Dataset<Row> p2 = reader2.load();
		p2.createOrReplaceTempView("like_notes");
		
		String table3 = "share_notes";
		DataFrameReader reader3 = sparkSession.read().format("jdbc");
		reader3.option("url", url);// 数据库路径
		reader3.option("dbtable", table3);// 数据表名
		reader3.option("driver", "com.mysql.jdbc.Driver");
		reader3.option("user", username);
		reader3.option("password", password);
		Dataset<Row> p3 = reader3.load();
		p3.createOrReplaceTempView("share_notes");
		
		String table4 = "like_users";
		DataFrameReader reader4 = sparkSession.read().format("jdbc");
		reader4.option("url", url);// 数据库路径
		reader4.option("dbtable", table4);// 数据表名
		reader4.option("driver", "com.mysql.jdbc.Driver");
		reader4.option("user", username);
		reader4.option("password", password);
		Dataset<Row> p4 = reader4.load();
		p4.createOrReplaceTempView("like_users");
		
		String table5 = "like_notebooks";
		DataFrameReader reader5 = sparkSession.read().format("jdbc");
		reader5.option("url", url);// 数据库路径
		reader5.option("dbtable", table5);// 数据表名
		reader5.option("driver", "com.mysql.jdbc.Driver");
		reader5.option("user", username);
		reader5.option("password", password);
		Dataset<Row> p5 = reader5.load();
		p5.createOrReplaceTempView("like_notebooks");
		
		String table6 = "like_colls";
		DataFrameReader reader6 = sparkSession.read().format("jdbc");
		reader6.option("url", url);// 数据库路径
		reader6.option("dbtable", table6);// 数据表名
		reader6.option("driver", "com.mysql.jdbc.Driver");
		reader6.option("user", username);
		reader6.option("password", password);
		Dataset<Row> p6 = reader6.load();
		p6.createOrReplaceTempView("like_colls");
		
		String table7 = "reward_notes";
		DataFrameReader reader7 = sparkSession.read().format("jdbc");
		reader7.option("url", url);// 数据库路径
		reader7.option("dbtable", table7);// 数据表名
		reader7.option("driver", "com.mysql.jdbc.Driver");
		reader7.option("user", username);
		reader7.option("password", password);
		Dataset<Row> p7 = reader7.load();
		p7.createOrReplaceTempView("reward_notes");
		
		String table8 = "like_comment";
		DataFrameReader reader8 = sparkSession.read().format("jdbc");
		reader8.option("url", url);// 数据库路径
		reader8.option("dbtable", table8);// 数据表名
		reader8.option("driver", "com.mysql.jdbc.Driver");
		reader8.option("user", username);
		reader8.option("password", password);
		Dataset<Row> p8 = reader8.load();
		p8.createOrReplaceTempView("like_comment");

		String spark_sql = " SELECT userid, '喜欢文章' as name, count(1) as value FROM like_notes group by userid having userid =  "+ user.getId()//喜欢文章
				+ " union "
				+ " SELECT userid,'发表评论' as name, count(1) as value FROM comment_notes group by userid having userid = "+ user.getId()//发表评论
				+ " union "
				+ " SELECT userid,'关注专题' as name, count(1) as value FROM like_colls group by userid having userid = "+ user.getId()//关注专题
				+ " union "
				+ " SELECT userid,'赞赏文章' as name, count(1) as value FROM reward_notes group by userid having userid = "+ user.getId()//赞赏文章
				+ " union "
				+ " SELECT userid,'喜欢文章' as name, count(1) as value FROM like_comment group by userid having userid = "+ user.getId()//喜欢文章
		        + " union "
		        + " SELECT userid,'发表文章' as name, count(1) as value FROM share_notes group by userid having userid = "+ user.getId()//发表文章
		        + " union "
		        + " SELECT userid,'关注用户' as name, count(1) as value FROM like_users group by userid having userid = "+ user.getId()//关注用户
		        + " union "
                + " SELECT userid,'关注文集' as name, count(1) as value FROM like_notebooks group by userid having userid = "+ user.getId();//关注文集
		// 执行sql查询
		Dataset<Row> dataset = sparkSession.sql(spark_sql);
		// dataset.show();
		// String zd = dataset.javaRDD().collect().toString();
		List<Row> collectAsList = dataset.collectAsList();
		Iterator<Row> iterator = collectAsList.iterator();
		while (iterator.hasNext()) {
			CateCount c = new CateCount();
			Row next = iterator.next();
			c.setName(next.getString(1));
			long long1 = next.getLong(2);
			int a = (int) long1;
			c.setValue(a);
			list.add(c);
		}
		sparkSession.close();
		return list;
	}

	// 02.每月动态次数
	@Override
	public MonthFreq everyMonthDT(User user) {
		String table1 = "comment_notes";
		Builder builder = SparkSession.builder().config(sparkConf);
		SparkSession sparkSession = builder.getOrCreate();// 单例模式创建一个SparkSession
		DataFrameReader reader = sparkSession.read().format("jdbc");
		reader.option("url", url);// 数据库路径
		reader.option("dbtable", table1);// 数据表名
		reader.option("driver", "com.mysql.jdbc.Driver");
		reader.option("user", username);
		reader.option("password", password);
		Dataset<Row> p = reader.load();
		p.createOrReplaceTempView("comment_notes");

		String table2 = "like_notes";
		DataFrameReader reader2 = sparkSession.read().format("jdbc");
		reader2.option("url", url);// 数据库路径
		reader2.option("dbtable", table2);// 数据表名
		reader2.option("driver", "com.mysql.jdbc.Driver");
		reader2.option("user", username);
		reader2.option("password", password);
		Dataset<Row> p2 = reader2.load();
		p2.createOrReplaceTempView("like_notes");
		
		String table3 = "share_notes";
		DataFrameReader reader3 = sparkSession.read().format("jdbc");
		reader3.option("url", url);// 数据库路径
		reader3.option("dbtable", table3);// 数据表名
		reader3.option("driver", "com.mysql.jdbc.Driver");
		reader3.option("user", username);
		reader3.option("password", password);
		Dataset<Row> p3 = reader3.load();
		p3.createOrReplaceTempView("share_notes");
		
		String table4 = "like_users";
		DataFrameReader reader4 = sparkSession.read().format("jdbc");
		reader4.option("url", url);// 数据库路径
		reader4.option("dbtable", table4);// 数据表名
		reader4.option("driver", "com.mysql.jdbc.Driver");
		reader4.option("user", username);
		reader4.option("password", password);
		Dataset<Row> p4 = reader4.load();
		p4.createOrReplaceTempView("like_users");
		
		String table5 = "like_notebooks";
		DataFrameReader reader5 = sparkSession.read().format("jdbc");
		reader5.option("url", url);// 数据库路径
		reader5.option("dbtable", table5);// 数据表名
		reader5.option("driver", "com.mysql.jdbc.Driver");
		reader5.option("user", username);
		reader5.option("password", password);
		Dataset<Row> p5 = reader5.load();
		p5.createOrReplaceTempView("like_notebooks");
		
		String table6 = "like_colls";
		DataFrameReader reader6 = sparkSession.read().format("jdbc");
		reader6.option("url", url);// 数据库路径
		reader6.option("dbtable", table6);// 数据表名
		reader6.option("driver", "com.mysql.jdbc.Driver");
		reader6.option("user", username);
		reader6.option("password", password);
		Dataset<Row> p6 = reader6.load();
		p6.createOrReplaceTempView("like_colls");
		
		String table7 = "reward_notes";
		DataFrameReader reader7 = sparkSession.read().format("jdbc");
		reader7.option("url", url);// 数据库路径
		reader7.option("dbtable", table7);// 数据表名
		reader7.option("driver", "com.mysql.jdbc.Driver");
		reader7.option("user", username);
		reader7.option("password", password);
		Dataset<Row> p7 = reader7.load();
		p7.createOrReplaceTempView("reward_notes");
		
		String table8 = "like_comment";
		DataFrameReader reader8 = sparkSession.read().format("jdbc");
		reader8.option("url", url);// 数据库路径
		reader8.option("dbtable", table8);// 数据表名
		reader8.option("driver", "com.mysql.jdbc.Driver");
		reader8.option("user", username);
		reader8.option("password", password);
		Dataset<Row> p8 = reader8.load();
		p8.createOrReplaceTempView("like_comment");
        String spark_sql = "select userid, time,sum(num) as freq from "
				+ " ("
				//发表评论
				+ " select userid,substring(time, 1, 7) as time,count(1) as num from comment_notes where userid = "+ user.getId()+ " group by substring(time, 1, 7),userid "
				+ " union all "
				//关注专题
				+ " select userid,substring(time, 1, 7) as time,count(1) as num from like_colls where userid = "+ user.getId()+ " group by substring(time, 1, 7),userid "
				+ " union all "
				//赞赏文章
				+ " select userid,substring(time, 1, 7) as time,count(1) as num from reward_notes where userid = "+ user.getId()+ " group by substring(time, 1, 7),userid "
				+ " union all "
				//点赞评论
				+ " select userid,substring(time, 1, 7) as time,count(1) as num from like_comment where userid = "+ user.getId()+ " group by substring(time, 1, 7),userid "
				+ " union all "
				//喜欢文章
				+ " select userid,substring(time, 1, 7) as time,count(1) as num from like_notes where userid = " + user.getId()+ " group by substring(time, 1, 7),userid "
				+ " union all "
				//发表文章
				+ " select userid,substring(time, 1, 7) as time,count(1) as num from share_notes where userid = " + user.getId()+ " group by substring(time, 1, 7),userid "
                + " union all "
				//关注用户
				+ " select userid,substring(time, 1, 7) as time,count(1) as num from like_users where userid = " + user.getId()+ " group by substring(time, 1, 7),userid "
				//关注文集
				+ " union all "
				+ " select userid,substring(time, 1, 7) as time,count(1) as num from like_notebooks where userid = " + user.getId()+ " group by substring(time, 1, 7),userid "
				+ ") taball "
				+ " group by time,userid order by time ";
		String sql = "SELECT userid, concat_ws(',',collect_list(time)) as mon, concat_ws(',',collect_list(freq)) as freq FROM  ("+ spark_sql + ") MonthFreq GROUP BY userid";
		Dataset<Row> dataset = sparkSession.sql(sql);
		// dataset.count();
		List<Row> collectAsList = dataset.collectAsList();
		Row row = collectAsList.get(0);
		MonthFreq monthFreq = new MonthFreq();
		monthFreq.setUserid(row.getInt(0));
		monthFreq.setMon(row.getString(1));
		monthFreq.setFreq(row.getString(2));
		// dataset.show();
		return monthFreq;
	}

	@Override
	public DayFreq everyDayDT(User user) {
		String table1 = "comment_notes";
		Builder builder = SparkSession.builder().config(sparkConf);
		SparkSession sparkSession = builder.getOrCreate();// 单例模式创建一个SparkSession
		DataFrameReader reader = sparkSession.read().format("jdbc");
		reader.option("url", url);// 数据库路径
		reader.option("dbtable", table1);// 数据表名
		reader.option("driver", "com.mysql.jdbc.Driver");
		reader.option("user", username);
		reader.option("password", password);
		Dataset<Row> p = reader.load();
		p.createOrReplaceTempView("comment_notes");

		String table2 = "like_notes";
		DataFrameReader reader2 = sparkSession.read().format("jdbc");
		reader2.option("url", url);// 数据库路径
		reader2.option("dbtable", table2);// 数据表名
		reader2.option("driver", "com.mysql.jdbc.Driver");
		reader2.option("user", username);
		reader2.option("password", password);
		Dataset<Row> p2 = reader2.load();
		p2.createOrReplaceTempView("like_notes");
		
		String table3 = "share_notes";
		DataFrameReader reader3 = sparkSession.read().format("jdbc");
		reader3.option("url", url);// 数据库路径
		reader3.option("dbtable", table3);// 数据表名
		reader3.option("driver", "com.mysql.jdbc.Driver");
		reader3.option("user", username);
		reader3.option("password", password);
		Dataset<Row> p3 = reader3.load();
		p3.createOrReplaceTempView("share_notes");
		
		String table4 = "like_users";
		DataFrameReader reader4 = sparkSession.read().format("jdbc");
		reader4.option("url", url);// 数据库路径
		reader4.option("dbtable", table4);// 数据表名
		reader4.option("driver", "com.mysql.jdbc.Driver");
		reader4.option("user", username);
		reader4.option("password", password);
		Dataset<Row> p4 = reader4.load();
		p4.createOrReplaceTempView("like_users");
		
		String table5 = "like_notebooks";
		DataFrameReader reader5 = sparkSession.read().format("jdbc");
		reader5.option("url", url);// 数据库路径
		reader5.option("dbtable", table5);// 数据表名
		reader5.option("driver", "com.mysql.jdbc.Driver");
		reader5.option("user", username);
		reader5.option("password", password);
		Dataset<Row> p5 = reader5.load();
		p5.createOrReplaceTempView("like_notebooks");
		
		String table6 = "like_colls";
		DataFrameReader reader6 = sparkSession.read().format("jdbc");
		reader6.option("url", url);// 数据库路径
		reader6.option("dbtable", table6);// 数据表名
		reader6.option("driver", "com.mysql.jdbc.Driver");
		reader6.option("user", username);
		reader6.option("password", password);
		Dataset<Row> p6 = reader6.load();
		p6.createOrReplaceTempView("like_colls");
		
		String table7 = "reward_notes";
		DataFrameReader reader7 = sparkSession.read().format("jdbc");
		reader7.option("url", url);// 数据库路径
		reader7.option("dbtable", table7);// 数据表名
		reader7.option("driver", "com.mysql.jdbc.Driver");
		reader7.option("user", username);
		reader7.option("password", password);
		Dataset<Row> p7 = reader7.load();
		p7.createOrReplaceTempView("reward_notes");
		
		String table8 = "like_comment";
		DataFrameReader reader8 = sparkSession.read().format("jdbc");
		reader8.option("url", url);// 数据库路径
		reader8.option("dbtable", table8);// 数据表名
		reader8.option("driver", "com.mysql.jdbc.Driver");
		reader8.option("user", username);
		reader8.option("password", password);
		Dataset<Row> p8 = reader8.load();
		p8.createOrReplaceTempView("like_comment");
		String spark_sql = " select userid, time,sum(num) as freq from "
				+ " ("
				//发表评论
				+ " select userid,substring(time, 1, 10) as time,count(1) as num from comment_notes  where userid = "+ user.getId()+ " group by substring(time, 1, 10),userid "
				+ " union all "
				//喜欢文章
				+ " select userid,substring(time, 1, 10) as time,count(1) as num from like_notes where userid = " + user.getId()+ " group by substring(time, 1, 10),userid"
				+ " union all "
				//发表文章
				+ " select userid,substring(time, 1, 10) as time,count(1) as num from share_notes where userid = " + user.getId()+ " group by substring(time, 1, 10),userid"
				+ " union all "
				//关注用户
				+ " select userid,substring(time, 1, 10) as time,count(1) as num from like_users where userid = " + user.getId()+ " group by substring(time, 1, 10),userid"
				+ " union all "
				//关注文集
				+ " select userid,substring(time, 1, 10) as time,count(1) as num from like_notebooks where userid = " + user.getId()+ " group by substring(time, 1, 10),userid"
				+ " ) taball "
				+ " group by time,userid order by time";
		String sql = " SELECT userid, concat_ws(',',collect_list(time)) as day,  concat_ws(',',collect_list(freq)) as freq "+ "FROM (" + spark_sql + ")DayFreq GROUP BY userid";
		Dataset<Row> dataset = sparkSession.sql(sql);
		List<Row> collectAsList = dataset.collectAsList();
		Row row = collectAsList.get(0);
		DayFreq dayFreq = new DayFreq();
		dayFreq.setUserid(row.getInt(0));
		dayFreq.setDay(row.getString(1));
		dayFreq.setFreq(row.getString(2));
		return dayFreq;
	}
	@Override
	public HourFreq getHourFreqByUserID(User user) {
		String table1 = "comment_notes";
		Builder builder = SparkSession.builder().config(sparkConf);
		SparkSession sparkSession = builder.getOrCreate();// 单例模式创建一个SparkSession
		DataFrameReader reader = sparkSession.read().format("jdbc");
		reader.option("url", url);// 数据库路径
		reader.option("dbtable", table1);// 数据表名
		reader.option("driver", "com.mysql.jdbc.Driver");
		reader.option("user", username);
		reader.option("password", password);
		Dataset<Row> p = reader.load();
		p.createOrReplaceTempView("comment_notes");

		String table2 = "like_notes";
		DataFrameReader reader2 = sparkSession.read().format("jdbc");
		reader2.option("url", url);// 数据库路径
		reader2.option("dbtable", table2);// 数据表名
		reader2.option("driver", "com.mysql.jdbc.Driver");
		reader2.option("user", username);
		reader2.option("password", password);
		Dataset<Row> p2 = reader2.load();
		p2.createOrReplaceTempView("like_notes");
		
		String table3 = "share_notes";
		DataFrameReader reader3 = sparkSession.read().format("jdbc");
		reader3.option("url", url);// 数据库路径
		reader3.option("dbtable", table3);// 数据表名
		reader3.option("driver", "com.mysql.jdbc.Driver");
		reader3.option("user", username);
		reader3.option("password", password);
		Dataset<Row> p3 = reader3.load();
		p3.createOrReplaceTempView("share_notes");
		
		String table4 = "like_users";
		DataFrameReader reader4 = sparkSession.read().format("jdbc");
		reader4.option("url", url);// 数据库路径
		reader4.option("dbtable", table4);// 数据表名
		reader4.option("driver", "com.mysql.jdbc.Driver");
		reader4.option("user", username);
		reader4.option("password", password);
		Dataset<Row> p4 = reader4.load();
		p4.createOrReplaceTempView("like_users");
		
		String table5 = "like_notebooks";
		DataFrameReader reader5 = sparkSession.read().format("jdbc");
		reader5.option("url", url);// 数据库路径
		reader5.option("dbtable", table5);// 数据表名
		reader5.option("driver", "com.mysql.jdbc.Driver");
		reader5.option("user", username);
		reader5.option("password", password);
		Dataset<Row> p5 = reader5.load();
		p5.createOrReplaceTempView("like_notebooks");
		
		String table6 = "like_colls";
		DataFrameReader reader6 = sparkSession.read().format("jdbc");
		reader6.option("url", url);// 数据库路径
		reader6.option("dbtable", table6);// 数据表名
		reader6.option("driver", "com.mysql.jdbc.Driver");
		reader6.option("user", username);
		reader6.option("password", password);
		Dataset<Row> p6 = reader6.load();
		p6.createOrReplaceTempView("like_colls");
		
		String table7 = "reward_notes";
		DataFrameReader reader7 = sparkSession.read().format("jdbc");
		reader7.option("url", url);// 数据库路径
		reader7.option("dbtable", table7);// 数据表名
		reader7.option("driver", "com.mysql.jdbc.Driver");
		reader7.option("user", username);
		reader7.option("password", password);
		Dataset<Row> p7 = reader7.load();
		p7.createOrReplaceTempView("reward_notes");
		
		String table8 = "like_comment";
		DataFrameReader reader8 = sparkSession.read().format("jdbc");
		reader8.option("url", url);// 数据库路径
		reader8.option("dbtable", table8);// 数据表名
		reader8.option("driver", "com.mysql.jdbc.Driver");
		reader8.option("user", username);
		reader8.option("password", password);
		Dataset<Row> p8 = reader8.load();
		p8.createOrReplaceTempView("like_comment");
		String spark_sql =" select userid, time,sum(num) as freq from "
				+ "("
				//发表评论
				+ " select userid,substring(time, 12, 2) as time,count(1) as num from comment_notes where userid = "+user.getId()+" group by substring(time, 12, 2),userid "
				+ " union all "
				//喜欢文章
				+ " select userid,substring(time, 12, 2) as time,count(1) as num from like_notes "+ " where userid = "+user.getId()+" group by substring(time, 12, 2),userid"
				+ " union all "
				//发表文章
				+ " select userid,substring(time, 12, 2) as time,count(1) as num from share_notes "+ " where userid = "+user.getId()+" group by substring(time, 12, 2),userid"
				+ " union all "
				//关注用户
				+ " select userid,substring(time, 12, 2) as time,count(1) as num from like_users "+ " where userid = "+user.getId()+" group by substring(time, 12, 2),userid"
				+ " union all "
				//关注文集
				+ " select userid,substring(time, 12, 2) as time,count(1) as num from like_notebooks "+ " where userid = "+user.getId()+" group by substring(time, 12, 2),userid"
				+ " ) taball group by time,userid order by time";
		String sql=" SELECT userid, concat_ws(',',collect_list(time)) as day,  concat_ws(',',collect_list(freq)) as freq FROM ("+spark_sql+")DayFreq GROUP BY userid ";
		Dataset<Row> dataset = sparkSession.sql(sql);
		List<Row> collectAsList = dataset.collectAsList();
		Row row = collectAsList.get(0);
		HourFreq hourFreq=new HourFreq();
		hourFreq.setUserid(row.getInt(0));
		hourFreq.setFreq(row.getString(2));
		hourFreq.setTime(row.getString(1));
		return hourFreq;
	}
	
	@Override
	//2.3版本以前的sparkSql不支持dayofweek函数！！！！！！！
	public WeekFreq getWeekFreqByUserID(User user) {
		String table1 = "comment_notes";
		Builder builder = SparkSession.builder().config(sparkConf);
		SparkSession sparkSession = builder.getOrCreate();// 单例模式创建一个SparkSession
		DataFrameReader reader = sparkSession.read().format("jdbc");
		reader.option("url", url);// 数据库路径
		reader.option("dbtable", table1);// 数据表名
		reader.option("driver", "com.mysql.jdbc.Driver");
		reader.option("user", username);
		reader.option("password", password);
		Dataset<Row> p = reader.load();
		p.createOrReplaceTempView("comment_notes");

		String table2 = "like_notes";
		DataFrameReader reader2 = sparkSession.read().format("jdbc");
		reader2.option("url", url);// 数据库路径
		reader2.option("dbtable", table2);// 数据表名
		reader2.option("driver", "com.mysql.jdbc.Driver");
		reader2.option("user", username);
		reader2.option("password", password);
		Dataset<Row> p2 = reader2.load();
		p2.createOrReplaceTempView("like_notes");
		
		String table3 = "share_notes";
		DataFrameReader reader3 = sparkSession.read().format("jdbc");
		reader3.option("url", url);// 数据库路径
		reader3.option("dbtable", table3);// 数据表名
		reader3.option("driver", "com.mysql.jdbc.Driver");
		reader3.option("user", username);
		reader3.option("password", password);
		Dataset<Row> p3 = reader3.load();
		p3.createOrReplaceTempView("share_notes");
		
		String table4 = "like_users";
		DataFrameReader reader4 = sparkSession.read().format("jdbc");
		reader4.option("url", url);// 数据库路径
		reader4.option("dbtable", table4);// 数据表名
		reader4.option("driver", "com.mysql.jdbc.Driver");
		reader4.option("user", username);
		reader4.option("password", password);
		Dataset<Row> p4 = reader4.load();
		p4.createOrReplaceTempView("like_users");
		
		String table5 = "like_notebooks";
		DataFrameReader reader5 = sparkSession.read().format("jdbc");
		reader5.option("url", url);// 数据库路径
		reader5.option("dbtable", table5);// 数据表名
		reader5.option("driver", "com.mysql.jdbc.Driver");
		reader5.option("user", username);
		reader5.option("password", password);
		Dataset<Row> p5 = reader5.load();
		p5.createOrReplaceTempView("like_notebooks");
		
		String table6 = "like_colls";
		DataFrameReader reader6 = sparkSession.read().format("jdbc");
		reader6.option("url", url);// 数据库路径
		reader6.option("dbtable", table6);// 数据表名
		reader6.option("driver", "com.mysql.jdbc.Driver");
		reader6.option("user", username);
		reader6.option("password", password);
		Dataset<Row> p6 = reader6.load();
		p6.createOrReplaceTempView("like_colls");
		
		String table7 = "reward_notes";
		DataFrameReader reader7 = sparkSession.read().format("jdbc");
		reader7.option("url", url);// 数据库路径
		reader7.option("dbtable", table7);// 数据表名
		reader7.option("driver", "com.mysql.jdbc.Driver");
		reader7.option("user", username);
		reader7.option("password", password);
		Dataset<Row> p7 = reader7.load();
		p7.createOrReplaceTempView("reward_notes");
		
		String table8 = "like_comment";
		DataFrameReader reader8 = sparkSession.read().format("jdbc");
		reader8.option("url", url);// 数据库路径
		reader8.option("dbtable", table8);// 数据表名
		reader8.option("driver", "com.mysql.jdbc.Driver");
		reader8.option("user", username);
		reader8.option("password", password);
		Dataset<Row> p8 = reader8.load();
		p8.createOrReplaceTempView("like_comment");
		
		String spark_sql ="select userid, time,sum(num) as freq from "
				+ " ( "
				+ " select userid,dayofweek(time) as time,count(1) as num from comment_notes where userid = 1 group by dayofweek(time),userid "//获得评论
				+ " union all "
				+ " select userid,dayofweek(time),count(1) as num from like_notes where userid = 1 group by dayofweek(time),userid "//关注文章
				+ " ) "
				+ " taball "
				+ " group by time,userid order by time ";
		String sql=" SELECT userid, concat_ws(',',collect_list(time)) as week,  concat_ws(',',collect_list(freq)) as freq "
				+ " from ("+spark_sql+") DayFreq GROUP BY userid ";
		Dataset<Row> dataset = sparkSession.sql(sql);
		List<Row> collectAsList = dataset.collectAsList();
		Row row = collectAsList.get(0);
		WeekFreq weekFreq=new WeekFreq();
		weekFreq.setUserid(row.getInt(0));
		weekFreq.setFreq(row.getString(2));
		weekFreq.setTime(row.getString(1));
		return weekFreq;
	}
 
	//评论-词云
	@Override
	public String CYun(User user) throws IOException {
		List<CateCount> CateCountlist=new ArrayList<CateCount>();
		// 某用户的全部评论信息
		List<CommentNotes> clist = commentNoteService
				.selectByPrimaryUserid(user.getId());
		// System.out.println(clist.size());
		List<String> str = new ArrayList<String>();
		Iterator<CommentNotes> iterator = clist.iterator();
		while (iterator.hasNext()) {
			CommentNotes next = iterator.next();
			String text = next.getCommentText();
			// 对字符串text进行分词
			StringReader sr = new StringReader(text);
			IKSegmenter ik = new IKSegmenter(sr, true);
			Lexeme lex = null;
			while ((lex = ik.next()) != null) {
				String t = lex.getLexemeText();
				if (t.length() > 1) {//单个字不获取
					str.add(t);
				}
			}
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String item : str) {
			if (map.containsKey(item)) {
				map.put(item, map.get(item).intValue() + 1);
			} else {
				map.put(item, new Integer(1));
			}
		}
		Iterator<String> keys = map.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			//System.out.println(key + ":" + map.get(key).intValue() + ", ");
			CateCount cateCount=new CateCount();
			cateCount.setName(key);
			cateCount.setValue(map.get(key).intValue());
			CateCountlist.add(cateCount);
            //[{"name":"","value":6},{"name":"评论","value":2},{"name":"为","value":2},
			//{"name":"表情","value":2},{"name":"加油","value":1},{"name":"gff","value":1}]
		}
		
		return JSON.toJSONString(CateCountlist);
	}

	

	
}
