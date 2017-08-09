import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dao2.Agency;
import com.util.SQL;
import com.util.Sparit;

public class d {
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String url = "http://www.xicidaili.com/";
		Sparit sparit = new Sparit();
		String result = sparit.sendGet(sparit.Connection(url));
//		System.out.println(result);
		Pattern r = Pattern.compile("<td>[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}</td>[\\s]*<td>[0-9]{0,4}</td>");
		Matcher m = r.matcher(result);
		Matcher ip = null;
		Matcher post = null;
		while(m.find()){
			r = Pattern.compile("[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}");
			ip = r.matcher(m.group());
			r = Pattern.compile("<td>[0-9]{0,4}</td>");
			post = r.matcher(m.group());
			if(ip.find() && post.find()){
				System.out.println("ip : " + ip.group() + "  post : " + post.group().substring(4, post.group().length()-5));
				try {
			           String surl="http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=834240768&FILE_NO=1995B00031AA&FILE_NO_TYPE=REG_TM&SOAPQC=1";
			           URL urls = new URL(surl);
			           InetSocketAddress addr = new InetSocketAddress(ip.group(), Integer.parseInt(post.group().substring(4, post.group().length()-5)));  
			           Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
			           URLConnection rulConnection  = urls.openConnection(proxy);
			           HttpURLConnection httpUrlConnection  =  (HttpURLConnection) rulConnection;
			           httpUrlConnection.setConnectTimeout(300000);
			           httpUrlConnection.setReadTimeout(300000);
			           httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");  
			           httpUrlConnection.connect();
			           String code = new Integer(httpUrlConnection.getResponseCode()).toString();
			           String message = httpUrlConnection.getResponseMessage();
			           System.out.println("getResponseCode code ="+ code);
			           if(code.startsWith("2")){
			        	   synchronized (Agency.proxys) {
			        		   Agency.proxys.add(proxy);
			        	   }
			           }
			               
			          }catch(Exception ex){
			               System.out.println(ex.getMessage());
			          }
			}
		}
		
	}

}
