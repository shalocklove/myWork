package com.dao;

import java.io.IOException;
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
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		URLConnection conn = null;
		try {
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
		String[] s = new String[8];
		s[0] = "<td width=\"460\">[\\S]{0,13}<";
		s[1] = "<b>状况：<br>Status:</b></td>[\\s]*<td>[\\s\\S]{0,25}</td>";
		s[2] = "<IMG src=\"[\\S]*\" border=\"0\" alt=\"image\">";
		s[3] = "<td nowrap><b>商标种类：<br>Mark Type:</b></td>[\\s]*<td>[\\s]*[\\S]*<br>";
		s[4] = "[0-9]{2}-[0-9]{2}-[0-9]{4}";
		s[5] = "姓名／名称、地址：<br>[\\s\\S]*Address:[\\s\\S]{0,500}<td>&nbsp;</td>";
		s[6] = "for Service:</b></td>[\\s\\S]{0,350}<td>&nbsp;</td>";
		s[7] = "<a href=\"javascript:;\" onClick=\"location.replace\\('#class_no[0-9]{0,3}";
		Map<String, String> map = new HashMap<String, String>();
		map.put("markNO", s[0]);
		map.put("status", s[1]);
		map.put("mark", s[2]);
		map.put("type", s[3]);
		map.put("date", s[4]);
		map.put("nameAddress", s[5]);
		map.put("service", s[6]);
		map.put("classNO", s[7]);
		Map<String, String> mmap = new HashMap<>();
		for (Map.Entry<String, String> ma : map.entrySet()) {
			r = Pattern.compile(ma.getValue());
			m = r.matcher(result);
			if(m.find()){
				mmap.put(ma.getKey(), m.group());
			}
		}
		
		mark.setMarkNO(mmap.get("markNO")+" ");
		mark.setClassNO(mmap.get("classNO"));
		mark.setDate(mmap.get("date"));
		mark.setMark(mmap.get("mark"));
		mark.setNameAddress(mmap.get("nameAddress"));
		mark.setService(mmap.get("service") + " ");
		mark.setStatus(mmap.get("status"));
		mark.setType(mmap.get("type"));
		MarkDAO markDao = new MarkDAO(mark);
		markDao.setMark(mark);
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
