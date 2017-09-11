package com.Agency;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLConnection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.Sparit;

public class FindAgency implements Runnable {
	
	public void run(){
		String result = "";
		String url = "http://www.xicidaili.com";
		Sparit sparit = new Sparit();
		String fip = null;
		int fpost = 0;
		URLConnection conn = null;
		ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 300,
			       TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
			    new ThreadPoolExecutor.CallerRunsPolicy());
		try {
			System.out.println(url);
			
			File file = new File("agency.txt");
				if(file.isFile() && file.exists()){
					InputStreamReader read = null;
		            try {
						read = new InputStreamReader(new FileInputStream(file));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            BufferedReader bufferedReader = new BufferedReader(read);
		            String lineTxt = null;
		            try {
						while((lineTxt = bufferedReader.readLine()) != null){
						    if(lineTxt.startsWith("post")){
						    	fpost = Integer.parseInt(lineTxt.split(":")[1]);
						    }else if(lineTxt.startsWith("ip")){
						    	fip = lineTxt.split(":")[1];
						    }
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            try {
						read.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		            InetSocketAddress addr = new InetSocketAddress(fip, fpost);  
			        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
			        conn = sparit.Connection(url,proxy);
				}else{
					conn = sparit.Connection(url);
				}
			
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
			conn.setRequestProperty("Cookie", "Hm_lvt_0cf76c77469e965d2957f0553e6ecf59=1502348697,1502421388,1502668730,1503446562; _free_proxy_session=BAh7B0kiD3Nlc3Npb25faWQGOgZFVEkiJWE4M2YwNzM1OWNiOGI0YWJkZGZhMmE3ZDVkNzVmYjBjBjsAVEkiEF9jc3JmX3Rva2VuBjsARkkiMWQyTGhFWGlnT2lod0IvRXRXUVQ5N3V0YTNHTnN3ZmFQSEpnbjlCQzl2OWc9BjsARg%3D%3D--9fd43de14b590785b3d426ce2c0c5d2c5f538341; Hm_lpvt_0cf76c77469e965d2957f0553e6ecf59=1503624254; AUTHSESSID=cc89541b1be1");
			conn.setRequestProperty("Host", "www.xicidaili.com");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
			conn.setRequestProperty("Cache-Control", "max-age=0");
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			result = sparit.sendGet(conn);
//			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pattern r = Pattern.compile("<td>[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}</td>[\\s]*<td>[0-9]{0,4}</td>");
		Matcher m = r.matcher(result);
		Matcher ip = null;
		Matcher post = null;
		String sip = "";
		String spost = "";
		while(m.find()){
			r = Pattern.compile("[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}");
			ip = r.matcher(m.group());
			r = Pattern.compile("<td>[0-9]{0,4}</td>");
			post = r.matcher(m.group());
			if(ip.find() && post.find()){
				sip = ip.group();
				spost = post.group().substring(4, post.group().length()-5);
				executor.execute(new Agency(sip, Integer.parseInt(spost)));
			}
		}
		executor.shutdown();
	}
}
	
	

