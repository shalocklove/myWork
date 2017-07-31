package com.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Bean.MarkBean;
import com.Bean.URLBean;
import com.util.SQL;
import com.util.Sparit;

public class SparitUrlDAO implements Runnable {
	private Sparit sparit = new Sparit();
	private String url = "";
	private String FILE_NO;
	private String key;
	private URLBean Url;
	List<URLBean> urls = new ArrayList<URLBean>();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(url != ""){
			try {
				String s = sparit.sendGet(sparit.Connection(url));
				String patternOocd = "KEY[\\S]*\" target=\"body\">";
				Pattern r = Pattern.compile(patternOocd);
				Matcher m = r.matcher(s);
				List<String> str = new ArrayList<String>();
				for(int i = 0; m.find(); i++){
					str.add(m.group());
				}
				for (String string2 : str) {
					System.out.println("aaaaa:"+string2);
					setFILE_NO(string2.split("&")[1].substring(8));
					setKey(string2.substring(4, 13));
					Url = new URLBean(key, FILE_NO);
					synchronized(urls){
						urls.add(Url);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url = "";
		}
	}
	public void setUrl(String url){
		this.url = url;
	}
	private void setKey(String s){
		this.key = s;
	}
	private void setFILE_NO(String s){
		this.FILE_NO = s;
	}
	public SparitUrlDAO (String url){
		setUrl(url);
	}
}
