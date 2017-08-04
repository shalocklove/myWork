package com.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.Sparit;

public class SparitUrlDAO implements Runnable {
	private Sparit sparit = new Sparit();
	private String url = "";
	private String Url;
	public static List<String> urls = new ArrayList<String>();
	//获取第二层url
	@Override
	public void run() {
		// TODO Auto-generated method stub
			try {
				String s = "";
				System.out.println(url);
				s = sparit.sendGet(sparit.Connection(url));
				
				String patternOocd = "ereg_main_schi.jsp?[\\S]{0,200}\"";
				Pattern r = Pattern.compile(patternOocd);
				Matcher m = r.matcher(s);
				List<String> str = new ArrayList<String>();
				
				while(m.find()){
					str.add(m.group().substring(19, m.group().length()-1));
					System.out.println("a : "+m.group().substring(19, m.group().length()-1));
				}
				
				for (String string2 : str) {
					Url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?" + string2;
					synchronized(urls){
						urls.add(Url);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(url);
			}
			
			
		
	}
	public void setUrl(String url){
		this.url = url;
	}
	public SparitUrlDAO (String url){
		setUrl(url);
	}
	public List<String> getUrlList(){
		return urls;
	}
	public static void removeURLBean(String url){
		synchronized(urls){
			urls.remove(url);
		}
	}
	
}
