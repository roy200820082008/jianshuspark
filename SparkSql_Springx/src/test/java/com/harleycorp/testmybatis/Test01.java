package com.harleycorp.testmybatis;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test01 {
	public static void main(String[] args) throws IOException {
		//http://www.jianshu.com/users/c4165d16d0ad/timeline?max_id=60047657&page=50
        Connection connect = Jsoup.connect("http://www.jianshu.com/users/c4165d16d0ad/timeline?max_id=60047657&page=50");
        Document document = connect.get();

        Elements elements2 = document.select(".note-list li");
        Element last = elements2.last();
        System.out.println("xxxx:"+last);
        
        if(elements2.last() == null) {
            System.out.println("www");
        }
	}

}
