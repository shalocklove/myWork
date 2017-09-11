package com.util;

import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class ThreadPost implements Runnable {

	private Sparit sparit = new Sparit();
	private String url;
	private Map<String,String>map = new HashMap<String,String>();
	private String encoding;
	private String result;
	private String parameter;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		sparit = new Sparit();
		try {
			URLConnection conn = sparit.Connection(url, map);
			result = sparit.sendPost(conn, parameter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ThreadPost(String url){
		this.url = url;
	}
	public ThreadPost(String url, Map<String, String>map){
		this.url = url;
		this.map = map;
	}
	public ThreadPost(String url, Map<String, String>map, String encoding){
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
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
