package com.util;

import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ThreadGet implements Runnable {

	private String url = "";
	private String result = "";
	private Map<String, String> map = new HashMap<String, String>();
	private String encoding = "utf-8"; 
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Sparit sparit = new Sparit();
		try {
			URLConnection conn = sparit.Connection(url, map);
			result = sparit.sendGet(conn, encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ThreadGet(String url){
		this.url = url;
	}
	public ThreadGet(String url, Map<String, String>map){
		this.url = url;
		this.map = map;
	}
	public ThreadGet(String url, Map<String, String>map, String encoding){
		this.url = url;
		this.map = map;
		this.encoding = encoding;
	}
	public void setEncoding(String encoding){
		this.encoding = encoding;
	}
	public String getEncoding(){
		return encoding;
	}
	public String getResult(){
		return result;
	}

}
