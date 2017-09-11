package com.Sparit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThirdSparit {
	
	private String captcha;
	private String postUrl;
	private String cookie;
	private String viewState;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String result = "";
	private String csfcfc = "";
	
	public ThirdSparit(String postUrl, String captcha, String cookie, String viewState) {
		this.captcha = captcha;
		this.postUrl = postUrl;
		this.cookie = cookie;
		this.cookie += " GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true";
		this.viewState = viewState;
	}
	
	public String sendPost(){
		try{
			URL realUrl = new URL(postUrl);
			// 打开和URL之间的连接
	        URLConnection conn = realUrl.openConnection();
	        // 设置通用的请求属性
	        conn.setRequestProperty("Host", "www.economia.gov.mo");
	        conn.setRequestProperty("Connection", "Keep-Alive");
	        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0");
	        conn.setRequestProperty("Accept", "application/xml, text/xml, */*; q=0.01");
	        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	        conn.setRequestProperty("Faces-Request", "partial/ajax");
	        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
	        conn.setRequestProperty("Referer", "https://www.economia.gov.mo/zh_CN/web/public/Pg_ES_AE_QE_TRADEMARK");
	        conn.setRequestProperty("Content-Length", "3000");
	        conn.setRequestProperty("Cookie", cookie);
	        // 发送POST请求必须设置如下两行
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        // 获取URLConnection对象对应的输出流
	        out = new PrintWriter(conn.getOutputStream());
	        // 发送请求参数
	        StringBuilder sb = new StringBuilder();
	        sb.append("javax.faces.partial.ajax=true&javax.faces.source=A9158:briefResultTable&javax.faces.partial.execute=A9158:briefResultTable&");
	        sb.append("javax.faces.partial.render=A9158:briefResultTable&A9158:briefResultTable=A9158:briefResultTable&");
	        sb.append("A9158:briefResultTable_pagination=true&A9158:briefResultTable_first=0&A9158:briefResultTable_rows=10&");
	        sb.append("A9158:briefResultTable_encodeFeature=true&A9158:marcaform=A9158:marcaform&javax.faces.encodedURL=" + postUrl + "&");
	        sb.append("A9158:applicationCategory=&A9158:j_idt39=&A9158:j_idt46=&A9158:j_idt48=&A9158:trademarkStyle=F&A9158:applicantChinese=&");
	        sb.append("A9158:applicantEnglish=&A9158:productCategory=0&A9158:inputViennaCode=&A9158:viennaTree_selection=&");
	        sb.append("A9158:matchAll=on&A9158:dsecaptchaText=&javax.faces.ViewState=" + viewState);
//	        out.write(sb.toString());
	        // flush输出流的缓冲
	        out.flush();
	        // 定义BufferedReader输入流来读取URL的响应
	        
	        in = new BufferedReader(
	                new InputStreamReader(conn.getInputStream(),"UTF-8"));
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
//	            System.out.println(line);
	        }
//	        System.out.println("ccc : " + conn.getHeaderField("Set-Cookie"));
	        Pattern r = Pattern.compile("csfcfc=[\\s\\S]{0,31};");
			Matcher m = r.matcher(conn.getHeaderField("Set-Cookie"));
			if(m.find()){
//				System.out.println(m.group().substring(7, m.group().length() - 1));
				csfcfc = m.group().substring(7, m.group().length() - 1);
			}//获取csfcf
		}catch(Exception e){
			System.out.println("出错，执行操作");
			
		}finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	public String getCsfcfc(){
		return csfcfc;
	}
}
