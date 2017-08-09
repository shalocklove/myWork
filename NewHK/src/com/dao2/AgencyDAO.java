package com.dao2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.Sparit;

public class AgencyDAO {
	
	public void run() throws IOException{
		String result = "";
		String url = "http://www.xicidaili.com/";
		Sparit sparit = new Sparit();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 300,
			       TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
			    new ThreadPoolExecutor.CallerRunsPolicy());
		try {
			result = sparit.sendGet(sparit.Connection(url));
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
