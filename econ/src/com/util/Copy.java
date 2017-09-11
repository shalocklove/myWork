package com.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public class Copy {
	private HttpURLConnection conn = null;
	private byte[] getData = null;
	
	public void copy(String url, String code, String type) throws IOException{
		URL u = new URL(url); 
        conn = (HttpURLConnection)u.openConnection(); 
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
	    conn.setRequestProperty("Host", "ipsearch.ipd.gov.hk");
	    conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	    conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	    conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
	    conn.setRequestProperty("Connection", "keep-alive");
	    conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
	    File dir = new File("tupian");
	    if(!dir.exists()){
	    	dir.mkdirs();
	    }
        InputStream inputStream = conn.getInputStream();   //通过输入流获得图片数据 
        byte[] getData = readInputStream(inputStream);     //获得图片的二进制数据 
        File imageFile = new File("tupian/"+ code +"." + type);   
        FileOutputStream fos = new FileOutputStream(imageFile);    
        fos.write(getData); 
        fos.close(); 
              
        System.out.println(" read picture success"); 
	}
	public void copyCaptcha(String url, String code, String type, String cookie) throws IOException{
		URL u = new URL(url); 
        conn = (HttpURLConnection)u.openConnection(); 
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0");  
	    conn.setRequestProperty("Host", "www.economia.gov.mo");
	    conn.setRequestProperty("Accept", "*/*");
	    conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	    conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
	    conn.setRequestProperty("Connection", "keep-alive");
	    conn.setRequestProperty("Referer", "https://www.economia.gov.mo/zh_CN/web/public/Pg_ES_AE_QE_TRADEMARK");
	    conn.setRequestProperty("Cookie", cookie + " GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true");
	    File dir = new File("tupian");
	    if(!dir.exists()){
	    	dir.mkdirs();
	    }
        InputStream inputStream = conn.getInputStream();   //通过输入流获得图片数据 
        byte[] getData = readInputStream(inputStream);     //获得图片的二进制数据 
        File imageFile = new File(code +"." + type);   
        FileOutputStream fos = new FileOutputStream(imageFile);    
        fos.write(getData); 
        fos.close(); 
              
        System.out.println(" read picture success"); 
	}
	public void copy(String url, String code, Proxy proxy, String type) throws IOException{
		URL u = new URL(url); 
		Boolean b = true;
		while(b){
		    try{
				conn = (HttpURLConnection)u.openConnection(proxy); 
		        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
			    conn.setRequestProperty("Host", "ipsearch.ipd.gov.hk");
			    conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			    conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			    conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			    conn.setRequestProperty("Connection", "keep-alive");
			    conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
			    conn.setConnectTimeout(70000);
			    conn.setReadTimeout(70000);
			    File dir = new File("tupian");
			    if(!dir.exists()){
			    	dir.mkdirs();
			    }
		        InputStream inputStream = conn.getInputStream();   //通过输入流获得图片数据 
		        getData = readInputStream(inputStream);     //获得图片的二进制数据
		        b = false;
		    }catch(Exception e){b = true;}    
		}
        File imageFile = new File("tupian/"+ code +"." + type);   
        FileOutputStream fos = new FileOutputStream(imageFile);    
        fos.write(getData); 
        fos.close(); 
              
        System.out.println(" read picture success"); 
	}
	public static  byte[] readInputStream(InputStream inputStream) throws IOException { 
        byte[] buffer = new byte[1024]; 
        int len = 0; 
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
        while((len = inputStream.read(buffer)) != -1) { 
            bos.write(buffer, 0, len); 
        }
              
        bos.close(); 
//        new Writer();
        return bos.toByteArray(); 
	}
}
