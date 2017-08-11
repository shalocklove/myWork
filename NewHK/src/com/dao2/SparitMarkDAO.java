package com.dao2;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Bean.MarkBean;
import com.util.Sparit;

public class SparitMarkDAO implements Runnable {
	private Pattern r = null;
	private Matcher m = null;
	private Sparit sparit = new Sparit();
	private MarkBean mark = new MarkBean();
	private String url = "";
	public static List<MarkBean> list = new ArrayList<MarkBean>();
	private Proxy proxy;
	private Agency agency;
	private HttpURLConnection httpUrlConnection;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		URLConnection conn = null;
		try {
			Thread.sleep(20000);
			if(agency.proxys.size() <= 3)
				Thread.sleep(2000);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		synchronized (agency.proxys) {
			proxy = (Proxy)agency.getProxs().get(1);
			agency.proxys.remove(proxy);
		}
		try {
			conn = sparit.Connection(url, proxy);
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
		    conn.setRequestProperty("Host", "ipsearch.ipd.gov.hk");
		    conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		    conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		    conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
		    conn.setRequestProperty("Referer", url);
//		    conn.setRequestProperty("Cookie", "USERID=GUEST; USER=TMLRUser; JSESSIONID=B7F4134322A647219A92D89E06466DBE");
		    conn.setRequestProperty("Connection", "keep-alive");
		    conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
//			conn = sparit.Connection(url, proxy);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		httpUrlConnection = (HttpURLConnection) conn;
		String result = "";
		try {
			result = sparit.sendGet(conn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("download" + url);
		String[] s = new String[15];
		s[0] = "<td width=\"460\">[\\S]{0,13}<";
		s[1] = "<b>状况：<br>Status:</b></td>[\\s]*<td>[\\s\\S]{0,25}</td>";
		s[2] = "<IMG src=\"[\\S]*\" border=\"0\" alt=\"image\">";
		s[3] = "<td nowrap><b>商标种类：<br>Mark Type:</b></td>[\\s]*<td>[\\s]*[\\S]*<br>";
		s[4] = "Registration:<br>\\(D-M-Y\\)[\\s]*</b></td>[\\s]*<td>[0-9]{2}-[0-9]{2}-[0-9]{4}";
		s[5] = "姓名／名称、地址：<br>[\\s\\S]*Address:[\\s\\S]{0,500}<td>&nbsp;</td>";
		s[6] = "for Service:</b></td>[\\s\\S]{0,350}<td>&nbsp;</td>";
		s[7] = "pecification:</b></td>[\\s\\S]*.<br><br>";
		s[8] = "Mark:[\\s\\S]*<IMG";
		s[9] = "Actual Date of <br>Registration:<br>\\(D-M-Y\\)[\\s]*</b></td>[\\s]*<td>[0-9]{2}-[0-9]{2}-[0-9]{4}";
		s[10] = "Expiry date:<br>\\(D-M-Y\\)[\\s]*</b></td>[\\s]*<td>[0-9]{2}-[0-9]{2}-[0-9]{4}";
		s[11] = "Date of Filing:<BR>\\(D-M-Y\\)[\\s]*</b></td>[\\s]*<td>[0-9]{2}-[0-9]{2}-[0-9]{4}";
		s[12] = "事项[\\s\\S]*<td>&nbsp;</td>";
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("markNO", s[0]);
		map.put("status", s[1]);
		map.put("mark", s[2]);
		map.put("type", s[3]);
		map.put("date", s[4]);//date
		map.put("nameAddress", s[5]);//包含address owner
		map.put("service", s[6]);
		map.put("class", s[7]);//表二 class   specification
		map.put("markname", s[8]);
		map.put("actualdate", s[9]);//实际注册日期
		map.put("expirydate", s[10]);//注册届满日
		map.put("fillingdate", s[11]);//提交日
		map.put("matters", s[12]);

		Map<String, String> mmap = new HashMap<>();
		for (Map.Entry<String, String> ma : map.entrySet()) {
			r = Pattern.compile(ma.getValue());
			m = r.matcher(result);
			if(m.find()){
				mmap.put(ma.getKey(), m.group());
			}
		}System.out.println("MARK : " + mmap.get("markNO") + "   url:" + url);
		mark.setMarkNO(mmap.get("markNO"));
		
		try{
			mark.setClass(mmap.get("class"));
		}catch(NumberFormatException e){
			System.out.println(mmap.get("class"));
		}
		
		if(mmap.get("date") != null)
			mark.setDate(mmap.get("date"));
		mark.setMark(mmap.get("mark"));
		mark.setNameAddress(mmap.get("nameAddress"));
		mark.setService(mmap.get("service") + " ");
		mark.setStatus(mmap.get("status"));
		mark.setType(mmap.get("type"));
		mark.setMarkName(mmap.get("markname"));
		if(mmap.get("actualdate") != null)
			mark.setActualdate(mmap.get("actualdate"));
		if(mmap.get("expirydate") != null)
			mark.setExpirydate(mmap.get("expirydate"));
		if(mmap.get("matters") != null)
			mark.setMatters(mmap.get("matters"));
		if(mmap.get("fillingdate") != null)
			mark.setFillingdate(mmap.get("fillingdate"));
		MarkDAO markDao = new MarkDAO(mark);
		markDao.run();
		
	}
	public static void remove(MarkBean mark){
		list.remove(mark);
	}
	public SparitMarkDAO(String url) {
		// TODO Auto-generated constructor stub
		this.url = url;
	}
	public MarkBean getMark(){
		return mark;
	}
}
