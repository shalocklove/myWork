package com.Agency;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import com.Bean.AgencyBean;
import com.util.Sparit;

public class Agency implements Runnable {
	private Proxy proxy;
	private static List<AgencyBean> agencys = new ArrayList<AgencyBean>();
	private String demoUrl = "https://www.economia.gov.mo/zh_CN/web/public/Pg_ES_AE_QE_TRADEMARK?_refresh=true#briefResultPanel";
	private String ip;
	private int post;
	private AgencyBean agency;
	private URL url;
	
	public Agency(String ip, int post){
		this.ip = ip;
		this.post = post;
	}
	public static List<AgencyBean> getAgencys() {
		return agencys;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			InetSocketAddress addr = new InetSocketAddress(ip, post); 
			proxy = new Proxy(Proxy.Type.HTTP, addr);
			Sparit sparit = new Sparit();
			try {
				String url = "https://www.economia.gov.mo/zh_CN/web/public/Pg_ES_AE_QE_TRADEMARK?_refresh=true";
				URLConnection conn = sparit.Connection(url, proxy);
				HttpURLConnection hconn = (HttpURLConnection)conn;
				hconn.setConnectTimeout(5000);
				hconn.setReadTimeout(5000);
				hconn.connect();
				String code = String.valueOf(hconn.getResponseCode());
				System.out.println("getResponseCode code ="+ code + "   " + ip + "   " + post);
				if(code.startsWith("2")){
					agency = new AgencyBean();
					agency.setIp(ip);
					agency.setPost(post);
					agency.setProxy(proxy);
					synchronized (agencys) {
						if(!agencys.contains(agency)){
							agencys.add(agency);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
	}
	
	
} 
