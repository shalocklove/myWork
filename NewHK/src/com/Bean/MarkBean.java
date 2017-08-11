package com.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkBean {
	private String markNO;//申请号
	private String status;//状况
	private String mark;//商标图片
	private String type;//商标种类
	private String nameAddress;//申请人 姓名／名称、地址
	private String service;//申请人送达地址
	private Map<Integer,String> classNO = new HashMap<Integer, String>(); // 类别编号
	private Date date;//提交日期 
	private String owner;//申请人姓名  （owner）
	private String address;//申请人地址
	private String markName;//商标名
	private Date actualdate;//实际日期
	private Date expirydate;//注册届满日
	private Date fillingdate;//提交日
	private Map<Date, String> matters = new HashMap<Date, String>();//事项
	private Pattern r;
	private Matcher m;
	public String getMarkNO() {
		return markNO;
	}
	public void setMarkNO(String markNO) {
		this.markNO = markNO.substring(16, markNO.length()-1);
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status.substring(41, status.length()-5);
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = "http://ipsearch.ipd.gov.hk/trademark" + mark.substring(12, mark.length() - 25);
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type.substring(90, type.length()-4);
	}
	public String getNameAddress() {
		return nameAddress;
	}
	public void setNameAddress(String nameAddress) {
		this.nameAddress = nameAddress;
		r = Pattern.compile("<td>[\\s\\S]*&nbsp");
		m = r.matcher(nameAddress);
		if(m.find()){
			this.owner = m.group().split("<br>")[0].substring(4);
			r = Pattern.compile("<br>[\\s\\S]*<br>");
			m = r.matcher(m.group());
			if(m.find()){
				this.address = m.group().split("</td>")[0];
			}
		}
	}
	public String getOwner(){
		return owner;
	}
	public String getAddress(){
		return address;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		r = Pattern.compile("<td>[\\s\\S]*<br>");
		m = r.matcher(service);
		if(m.find()){
			this.service = m.group().substring(6);
		}
	}
	public Map<Integer, String> getClassNO() {
		return classNO;
	}
	public void setClass(String classNO) {
		String[] z = classNO.split("<a name=\"class_no");
		for(int i = 1; i < z.length; i++){
			this.classNO.put(Integer.valueOf(z[i].split("</u><br>")[0].substring(z[i].split("</u><br>")[0].length()-2)), z[i].split("</u><br>")[1].split("<br>")[0]);
		}
	}
	public Date getDate() {
		return date;
	}
	public void setDate(String date) {
		r = Pattern.compile("[0-9]{2}-[0-9]{2}-[0-9]{4}");
		m = r.matcher(date);
		if(m.find()) date = m.group();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			this.date = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getMarkName() {
		return markName;
	}
	public void setMarkName(String markName) {
		this.markName = markName.split("<br>")[0].split("<td>")[1];
	}
	public Date getActualdate() {
		return actualdate;
	}
	public void setActualdate(String actualdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			this.actualdate = sdf.parse(actualdate.substring(actualdate.length() - 10));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Date getExpirydate() {
		return expirydate;
	}
	public void setExpirydate(String expirydate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			this.expirydate = sdf.parse(expirydate.substring(expirydate.length() - 10));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Map<Date, String> getMatters() {
		return matters;
	}
	public void setMatters(String matters) {
		String[] st = matters.split("<td colspan=\"2\">&nbsp</td>");
		for(int i = 1; i < st.length; i++){
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date d = null;
			try {
				d = sdf.parse(st[i].split("<td>")[0].substring(st[i].split("<td>")[0].length() - 26, st[i].split("<td>")[0].length() - 16));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String q = st[i].split("<td>")[1].split(">")[1];			
			this.matters.put(d, q.substring(1, q.length() - 12));
		}
	}
	public Date getFillingdate() {
		return fillingdate;
	}
	public void setFillingdate(String fillingdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			this.fillingdate = sdf.parse(fillingdate.substring(fillingdate.length() - 10));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
