package com.dao;

import java.io.IOException;
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
	private InetSocketAddress addr;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		URLConnection conn = null;
		try {
			addr = new InetSocketAddress("118.178.124.33",3128);  
	        proxy = new Proxy(Proxy.Type.HTTP, addr);
			conn = sparit.Connection(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
//		System.out.println(result);
		Map<String, String> mmap = new HashMap<>();
		for (Map.Entry<String, String> ma : map.entrySet()) {
			r = Pattern.compile(ma.getValue());
			m = r.matcher(result);
			if(m.find()){
				mmap.put(ma.getKey(), m.group());
			}
		}
		mark.setMarkNO(mmap.get("markNO")+" ");
		mark.setClass(mmap.get("class"));
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
