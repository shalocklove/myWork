package com.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkBean {
	private String markNO;//�����
	private String status;//״��
	private String mark;//�̱�ͼƬ
	private String type;//�̱�����
	private String nameAddress;//������ ���������ơ���ַ
	private String service;//�������ʹ��ַ
	private String classNO; // �����
	private Date date;//�ύ����
	private String name;//����������
	private String address;//�����˵�ַ
	private Pattern r;
	private Matcher m;
	public String getMarkNO() {
		return markNO;
	}
	public void setMarkNO(String markNO) {
		this.markNO = markNO.substring(16, markNO.length()-2);
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
			this.name = m.group().split("<br>")[0].substring(4);
			r = Pattern.compile("<br>[\\s\\S]*<br>");
			m = r.matcher(m.group());
			if(m.find()){
				this.address = m.group();
			}
		}
	}
	public String getName(){
		return name;
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
	public String getClassNO() {
		return classNO;
	}
	public void setClassNO(String classNO) {
		this.classNO = classNO.substring(59);
	}
	public Date getDate() {
		return date;
	}
	public void setDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			this.date = sdf.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
