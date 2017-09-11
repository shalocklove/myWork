package com.Sparit;

import java.io.IOException;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.Copy;
import com.util.Sparit;

public class FirstSparit {
	private Sparit sparit;
	private String url = "https://www.economia.gov.mo/zh_CN/web/public/Pg_ES_AE_QE_TRADEMARK";
	private String result = "";
	private URLConnection conn = null;
	
	public void setResult(){
		sparit = new Sparit();
		try {
			conn = sparit.Connection(url);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			result = sparit.sendGet(conn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void setCaptcha(String cookie){//验证码图片
		Pattern r = Pattern.compile("/MarcaQueryPortlet-portlet/DSECaptcha.jpg?[\\S]*\"");
		Matcher m = r.matcher(result);
		System.out.println();
		if(m.find()){
			Copy copy = new Copy();
			try {
//				System.out.println("https://www.economia.gov.mo" + m.group().substring(0, m.group().length()-1));
				copy.copyCaptcha("https://www.economia.gov.mo" + m.group().substring(0, m.group().length()-1), "Captcha",
						"jpg", cookie);//下载图片
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getPostUrl(){//javax.faces.encodedURL
		Pattern r = Pattern.compile("action=[\\S]*\\?");
		Matcher m = r.matcher(result);
		if(m.find())
			return m.group().substring(8);
		return null;
	}
	
	public String getViewState(){
		Pattern r = Pattern.compile("[-]{0,1}[0-9]{19}:[-]{0,1}[0-9]{19}");
		Matcher m = r.matcher(result);
		if(m.find())
			return m.group();
		return null;
	}
	
	public String cookies(){//获取JSESSIONID（）
		Pattern r = Pattern.compile("JSESSIONID=[\\S]{35,39};");
		for(String s : conn.getHeaderFields().get("Set-Cookie")){
			Matcher m = r.matcher(s);
			while(m.find())
				return m.group();
		}
		return null;
	}
	
	public void doSomeThing(){
		setResult();
		
	}
}
