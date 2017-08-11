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
//		String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=830513920&FILE_NO=199200099&FILE_NO_TYPE=REG_TM&SOAPQC=0";
//		String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=830517504&FILE_NO=199200117&FILE_NO_TYPE=REG_TM&SOAPQC=3";
	    String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=830520064&FILE_NO=199200126&FILE_NO_TYPE=REG_TM&SOAPQC=7";
//		String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=830517760&FILE_NO=199200118&FILE_NO_TYPE=REG_TM&SOAPQC=3";
					
		Sparit sparit = new Sparit();
		URLConnection conn = sparit.Connection(url);
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:54.0) Gecko/20100101 Firefox/54.0");  
	    conn.setRequestProperty("Host", "ipsearch.ipd.gov.hk");
	    conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	    conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
	    conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
	    conn.setRequestProperty("Referer", url);
//	    conn.setRequestProperty("Cookie", "USERID=GUEST; USER=TMLRUser; JSESSIONID=A31CEB1DEE21AD25A4ED1DF7921E4BB9");
//	    conn.setRequestProperty("Cookie", "USERID=GUEST; USER=TMLRUser; JSESSIONID=90D08BA5FB49E8B08F0FC52D6632E4F2");
	    conn.setRequestProperty("Cookie", "USERID=GUEST; USER=TMLRUser; JSESSIONID=33A1B9FE8F4FE95B7743A6CE3B13D7ED");
	    conn.setRequestProperty("Connection", "keep-alive");
	    conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
	    String result = sparit.sendGet(conn);
	    System.out.println(result);
	    Pattern r = Pattern.compile("<td width=\"460\">[\\S]{0,13}<");
	    Matcher m = r.matcher(result);
	    if(m.find()){
	    	System.out.println("aaaa :  " + m.group());
	    	System.out.println(m.group().substring(16, m.group().length()-2));
	    }
	    

	}

}
