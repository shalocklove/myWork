package com.Sparit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
//目标：输入验证码，获取cssfcfc
public class SecondSparit {
	
	private String result = "";
	private String postUrl;
	private String captcha;
	private String cookie;
	private String viewState;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String csfcfc = "";
	
	public SecondSparit(String postUrl, String captcha, String cookie, String viewState) {
		this.captcha = captcha;
		this.postUrl = postUrl;
		this.cookie = cookie;
		this.cookie += " GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true";
		this.viewState = viewState;
		System.out.println(postUrl);
	}
	
	public String getCsfcfc(){
		return csfcfc;
	}
	
	public String sendPost(){
		System.out.println("Cookie : " + cookie);
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
	        conn.setRequestProperty("Content-Length", "3000");
	        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
	        conn.setRequestProperty("Referer", "https://www.economia.gov.mo/zh_CN/web/public/Pg_ES_AE_QE_TRADEMARK");
	        conn.setRequestProperty("Content-Length", "40000");
	        conn.setRequestProperty("Cookie", cookie);
	        // 发送POST请求必须设置如下两行
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        // 获取URLConnection对象对应的输出流
	        out = new PrintWriter(conn.getOutputStream());
	        // 发送请求参数
	        StringBuilder sb = new StringBuilder();
	        sb.append("javax.faces.partial.ajax=true&javax.faces.source=A9158%3Aj_idt78&javax.faces.partial.execute=%40all&");// 向页面传递数据。post的关键所在！
	        sb.append("javax.faces.partial.render=A9158%3Adsecaptcha+A9158%3AdsecaptchaText+A9158%3AbriefResultPanel+A9158%3AcaptchaDialogID&");
	        sb.append("A9158%3Aj_idt78=A9158%3Aj_idt78&A9158%3Amarcaform=A9158%3Amarcaform&");
	        sb.append("javax.faces.encodedURL=" + postUrl + "&");
	        sb.append("A9158%3AapplicationCategory=&A9158%3Aj_idt39=&A9158%3Aj_idt46=&A9158%3Aj_idt48=&A9158%3AtrademarkStyle=M&");//会更改
	        sb.append("A9158%3AapplicantChinese=&A9158%3AapplicantEnglish=&A9158%3AproductCategory=0&A9158%3AinputViennaCode=&");
	        sb.append("A9158%3AviennaTree_selection=&A9158%3AmatchAll=on&A9158%3AdsecaptchaText="+ captcha +"&javax.faces.ViewState=" + viewState);
	        out.write(sb.toString());  
//	        System.out.println("a    : " +sb);
	        // flush输出流的缓冲
	        out.flush();
	        // 定义BufferedReader输入流来读取URL的响应
	        
	        in = new BufferedReader(
	                new InputStreamReader(conn.getInputStream(),"UTF-8"));
	        Pattern r = Pattern.compile("csfcfc=[\\s\\S]{0,40};");
			Matcher m = r.matcher(conn.getHeaderField("Set-Cookie"));
			if(m.find()){
				System.out.println(m.group().substring(7, m.group().length() - 1));
				csfcfc = m.group().substring(7, m.group().length() - 1);
			}//获取csfcfc
			else
				System.out.println("重新开始");
			
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
	
	
}
