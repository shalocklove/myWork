package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Properties;

public class Sparit {
	private URL url;
	private URLConnection conn;
	private String setChar = "UTF-8";
	//爬虫建立链接
	public URLConnection Connection(String urlString) throws IOException{
		url = new URL(urlString);
//		Proxy proxy = Proxy.getProxyClass(loader, interfaces)
		conn = url.openConnection();
		return conn;
	}
	//利用代理
	public URLConnection Connection(String urlString, Proxy proxy) throws IOException{
		url = new URL(urlString);
		conn = url.openConnection(proxy);
		return conn;
	}
	//爬虫建立链接设置头请求
	public URLConnection Connection(String urlString, Map<String, String> map) throws IOException{
		conn = Connection(urlString);
		for(Map.Entry<String, String>m : map.entrySet()){
			conn.setRequestProperty(m.getKey(), m.getValue());
		}
		conn.setDoInput(true);
		conn.setDoOutput(true);
		return conn;
	}
	//发送get请求
	public String sendGet(URLConnection conn, String setChar) throws IOException{
		this.setChar = setChar;
		return sendGet(conn);
	}
	public String sendGet(URLConnection conn) throws IOException{
		StringBuffer result = new StringBuffer();
	    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
		conn.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),setChar));
		String line;
		while((line = in.readLine()) != null){
			result.append(line + "\n");
		}
		return result.toString();
	}
	public String sendPost(URLConnection conn, String parameter) throws IOException{
		conn.setRequestProperty("User-Agent", "Mozilla/31.0 (compatible; MSIE 10.0; Windows NT; DigExt)");  
		conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		StringBuffer resulr = new StringBuffer();
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.println(parameter);
		out.flush();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while((line = in.readLine()) != null){
        	resulr.append(line);
        }
		return resulr.toString();
	}
	
}
//Python NO.1
//楼上说的对
//虽然我们以java驱动
//+1
//人生苦短
 