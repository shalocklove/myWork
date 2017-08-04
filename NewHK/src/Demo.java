import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Bean.MarkBean;
import com.dao.MarkDAO;
import com.util.Sparit;

public class Demo {

	private static Pattern r = null;
	private static Matcher m = null;
	private static Sparit sparit = new Sparit();
	private static MarkBean mark = new MarkBean();
	public static List<MarkBean> list = new ArrayList<MarkBean>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=834240768&FILE_NO=1995B00031AA&FILE_NO_TYPE=REG_TM&SOAPQC=1";
		String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=819205120&FILE_NO=18740001&FILE_NO_TYPE=REG_TM&SOAPQC=0";
//		String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=834241792&FILE_NO=1995B00036&FILE_NO_TYPE=REG_TM&SOAPQC=1";
//		String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=102405376&FILE_NO=19903148&FILE_NO_TYPE=TM_APPL&SOAPQC=0";
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
		String[] s = new String[13];
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
		mark.setDate(mmap.get("date"));
		mark.setMark(mmap.get("mark"));
		mark.setNameAddress(mmap.get("nameAddress"));
		mark.setService(mmap.get("service") + " ");
		mark.setStatus(mmap.get("status"));
		mark.setType(mmap.get("type"));
		mark.setMarkName(mmap.get("markname"));
//		mark.setActualdate(mmap.get("actualdate"));
		mark.setExpirydate(mmap.get("expirydate"));
		
//		System.out.println("matters : " + mmap.get("matters"));
		String[] st = mmap.get("matters").split("<td colspan=\"2\">&nbsp</td>");
		for(int i = 1; i < st.length; i++){
			System.out.println("V : " + st[i].split("<td>")[0].substring(st[i].split("<td>")[0].length() - 26
					, st[i].split("<td>")[0].length() - 16));
			String q = st[i].split("<td>")[1].split(">")[1];
			System.out.println("S : " + q.substring(1, q.length() - 12));
		}
		
	}

}
