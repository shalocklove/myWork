package com.Sparit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpConnection;

import com.util.Sparit;

public class SparitMassage {
	
	private String result = "";
	private Sparit sparit = new Sparit();
	private PrintWriter out = null;
	private BufferedReader in = null;
	
	public String getResult(String url, String cookie, String viewState, String csfcfc){
		try {
			int i = 1;
			URL realUrl = new URL(url + "p_p_id=MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1&_MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l__jsfBridgeAjax=true&_MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l__facesViewIdResource=/index.xhtml");
			System.out.println("url ; " + url );
			// 打开和URL之间的连接
	        URLConnection conn = realUrl.openConnection();
	        conn.setRequestProperty("Host", "www.economia.gov.mo");
	        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:55.0) Gecko/20100101 Firefox/55.0");
	        conn.setRequestProperty("Accept", "application/xml, text/xml, */*; q=0.01");
	        conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//	        conn.setRequestProperty("Accept-Encoding", "gzip");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	        conn.setRequestProperty("Faces-Request", "partial/ajax");
	        conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
	        conn.setRequestProperty("Referer", "https://www.economia.gov.mo/zh_CN/web/public/Pg_ES_AE_QE_TRADEMARK?_refresh=true");
//	        conn.setRequestProperty("Content-Length", "3800");
	        conn.setRequestProperty("Cookie", cookie + " GUEST_LANGUAGE_ID=zh_CN; COOKIE_SUPPORT=true; csfcfc=" + csfcfc);
	        conn.setRequestProperty("Connection", "Keep-Alive");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);
	        out = new PrintWriter(conn.getOutputStream());
	        String a = "javax.faces.partial.ajax=true&javax.faces.source=A9158%3AbriefResultTable%3A" + i + "%3Adetail_&javax.faces.partial.execute=%40all&A9158%3AbriefResultTable%3A" + i + "%3Adetail_=A9158%3AbriefResultTable%3A" + i + "%3Adetail_&A9158%3Amarcaform=A9158%3Amarcaform&javax.faces.encodedURL=" + url + "p_p_id=MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1&_MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l__jsfBridgeAjax=true&_MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l__facesViewIdResource=%2Findex.xhtml&A9158%3AapplicationCategory=&A9158%3Aj_idt39=&A9158%3Aj_idt46=&A9158%3Aj_idt48=&A9158%3AtrademarkStyle=M&A9158%3AapplicantChinese=&A9158%3AapplicantEnglish=&A9158%3AproductCategory=0&A9158%3AinputViennaCode=&A9158%3AviennaTree_selection=&A9158%3AmatchAll=on&A9158%3AdsecaptchaText=&javax.faces.ViewState=" + viewState.replace(":", "%3A");
	        System.out.println(a);
	        out.write(a);
	        out.flush();
	        in = new BufferedReader(
	                new InputStreamReader(conn.getInputStream(), "utf-8"));
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	            System.out.println(line);
	        }
	        return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}









//我进不去了！！怎么办！！好烦！！！！！
