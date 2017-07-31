package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class Sparit {
	private URL url;
	private URLConnection conn;
	private String setChar = "UTF-8";
	//���潨������
	public URLConnection Connection(String urlString) throws IOException{
		url = new URL(urlString);
		conn = url.openConnection();
		return conn;
	}
	//���潨����������ͷ����
	public URLConnection Connection(String urlString, Map<String, String> map) throws IOException{
		conn = Connection(urlString);
		for(Map.Entry<String, String>m : map.entrySet()){
			conn.setRequestProperty(m.getKey(), m.getValue());
		}
		conn.setDoInput(true);
		conn.setDoOutput(true);
		return conn;
	}
	//����get����
	public String sendGet(URLConnection conn, String setChar) throws IOException{
		this.setChar = setChar;
		return sendGet(conn);
	}
	public String sendGet(URLConnection conn) throws IOException{
		String result = "";
		conn.connect();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),setChar));
		String line;
		while((line = in.readLine()) != null){
			result += line + "\n";
		}
		return result;
	}
	public String sendPost(URLConnection conn, String parameter) throws IOException{
		String resulr = "";
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.println(parameter);
		out.flush();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while((line = in.readLine()) != null){
        	resulr += line;
        }
		return resulr;
	}
	
}
//Python NO.1
//¥��˵�Ķ�
//��Ȼ������java����
//+1
//�������
 