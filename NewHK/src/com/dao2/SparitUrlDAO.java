package com.dao2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.Sparit;

public class SparitUrlDAO implements Runnable {
	private Sparit sparit = new Sparit();
	private String url = "";
	private String Url;
//	public static List<String> urls = new ArrayList<String>();
	public List<String> urls = new ArrayList<String>();
	private Proxy proxy;
	private Agency agency = new Agency();
	private URLConnection conn;
	//获取第二层url
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			try {
				try {
					Thread.sleep(20000);
					if(agency.proxys.size() <= 3)
						Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String s = "";
				System.out.println(url);
				synchronized (agency.proxys) {
					proxy = (Proxy)agency.getProxs().get(0);
					agency.proxys.remove(proxy);
				}
			    conn = sparit.Connection(url, proxy);
			    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
			    conn.setRequestProperty("Host", "ipsearch.ipd.gov.hk");
			    conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			    conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			    conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			    conn.setRequestProperty("Referer", url);
//			    conn.setRequestProperty("Cookie", "USERID=GUEST; USER=TMLRUser; JSESSIONID=B7F4134322A647219A92D89E06466DBE");
			    conn.setRequestProperty("Connection", "keep-alive");
			    conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
//				conn = sparit.Connection(url, proxy);
				
				s = sparit.sendGet(conn);
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
	public void removeURLBean(String url){
		synchronized(urls){
			urls.remove(url);
		}
	}
	
}
//曾经只有我和上帝知道这是什么
//
//
//
//
//
//现在，只有上帝了
