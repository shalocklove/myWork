package com.dao2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.Sparit;

public class Agency implements Runnable {

	public static List<Proxy> proxys = new ArrayList<Proxy>();
	private String ip;
	private String surl="http://ipsearch.ipd.gov.hk/trademark/jsp/fssr01001s_schi.jsp?SOAPQC=-1&FILE_NO_TYPE=A&FILE_NO=19920010&FILE_NO_SMM=5&FILE_NO=19920019&FILE_NO_SMM=3&TM_TEXT=&TM_TEXT_SMM=0&TM_TEXT_OP=0&TM_TEXT=&TM_TEXT_SMM=0&TM_TEXT_OP=0&TM_TEXT=&TM_TEXT_SMM=0&TM_CODE=&TM_CODE_OP=1&CLASS_NO=&MARK_TYPE=Y&SPECIAL_SIGNS=Y&COLOUR_CLAIM_DESCRIP_STYLE=&COLOUR_CLAIM_DESCRIP_STYLE_SMM=0&APPL_NAME=&APPL_NAME_SMM=0&LICENCEE=&LICENCEE_SMM=0&ADD_FOR_SERVICE=&ADD_FOR_SERVICE_SMM=0&FILE_DT=&FILE_DT_SMM=5&FILE_DT=&FILE_DT_SMM=3&RREG_DT=&RREG_DT_SMM=5&RREG_DT=&RREG_DT_SMM=3&EXP_DT=&EXP_DT_SMM=5&EXP_DT=&EXP_DT_SMM=3&PUB_DT=&PUB_DT_SMM=5&PUB_DT=&PUB_DT_SMM=3&TM_SPEC=&TM_SPEC_SMM=0&TM_DIS=&TM_DIS_SMM=6&STYPE=S&TYPE=S&LIVE_STATUS=Y";
	private int post;
	public static List getProxs() {
		return proxys;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		URL urls = null;
		try {
			urls = new URL(surl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.out.println("ip : " + ip + "  post : " + post);
	        InetSocketAddress addr = new InetSocketAddress(ip, post);  
	        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
	        try{
	        URLConnection rulConnection  = urls.openConnection(proxy);
	        HttpURLConnection httpUrlConnection  =  (HttpURLConnection) rulConnection;
	        httpUrlConnection.setConnectTimeout(1000);
	        httpUrlConnection.setReadTimeout(2000);
	        httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");  
	        httpUrlConnection.connect();
	        String code = new Integer(httpUrlConnection.getResponseCode()).toString();
	        System.out.println("getResponseCode code ="+ code + "   " + ip);
        if(code.startsWith("2")){
            synchronized (proxys) {
            	proxys.add(proxy);
			}
        }
        }catch(IOException e){
        	
        }
	}
	public Agency(String ip, int post){
		this.ip = ip;
		this.post = post;
	}
	public Agency() {
		// TODO Auto-generated constructor stub
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}

}
