package com.harleycorp.testmybatis;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
	public static void main(String[] args) throws IOException {
		int maxid = 0;
		int pageno = 1;

		String query = "?max_id=" + maxid + "&page=" + pageno;

		Connection connect = Jsoup
				.connect("http://www.jianshu.com/users/08e6960f7ed9/timeline"
						+ query);

		// 得到Document对象
		Document document = connect.get();
		System.out.println(document.toString());

		Elements elements2 = document.select(".note-list li");
		if (elements2.last() == null) {
			return;
		}
		String id = elements2.last().id();
		// 这个值是翻页除了page的另一个参数，第二页的值在第一页、第三页的值在第二页
		// 将这个值获取到，传给下一个循环即可
		maxid = Integer.parseInt(id.split("-")[1]) - 1;
		for (Element element : elements2) {

			// System.out.println(element.toString());
			Elements elements3 = element.select("span[data-type=like_comment]");
			if (elements3.size() > 0) {
				System.out.println("喜欢了评论");
			}

		}
	}
}
