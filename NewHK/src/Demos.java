import java.io.IOException;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Bean.MarkBean;
import com.dao.MarkDAO;
import com.util.Sparit;

public class Demos {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Pattern r = null;
		Matcher m = null;
		Sparit sparit = new Sparit();
		MarkBean mark = new MarkBean();
		int i;
		String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/fssr01001s_schi.jsp?SOAPQC=-1&FILE_NO_TYPE=A&FILE_NO=18970189&FILE_NO_SMM=5&FILE_NO=18970189&FILE_NO_SMM=3&TM_TEXT=&TM_TEXT_SMM=0&TM_TEXT_OP=0&TM_TEXT=&TM_TEXT_SMM=0&TM_TEXT_OP=0&TM_TEXT=&TM_TEXT_SMM=0&TM_CODE=&TM_CODE_OP=1&CLASS_NO=&MARK_TYPE=Y&SPECIAL_SIGNS=Y&COLOUR_CLAIM_DESCRIP_STYLE=&COLOUR_CLAIM_DESCRIP_STYLE_SMM=0&APPL_NAME=&APPL_NAME_SMM=0&LICENCEE=&LICENCEE_SMM=0&ADD_FOR_SERVICE=&ADD_FOR_SERVICE_SMM=0&FILE_DT=&FILE_DT_SMM=5&FILE_DT=&FILE_DT_SMM=3&RREG_DT=&RREG_DT_SMM=5&RREG_DT=&RREG_DT_SMM=3&EXP_DT=&EXP_DT_SMM=5&EXP_DT=&EXP_DT_SMM=3&PUB_DT=&PUB_DT_SMM=5&PUB_DT=&PUB_DT_SMM=3&TM_SPEC=&TM_SPEC_SMM=0&TM_DIS=&TM_DIS_SMM=6&STYPE=S&TYPE=S&LIVE_STATUS=Y";
		String url2 = "http://ipsearch.ipd.gov.hk/trademark/jsp/fssr01001s_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&TYPE=S&STYPE=S&SOAPQC=1&LIVE_STATUS=Y&PAGE_NO=2";
		
		URLConnection conn = sparit.Connection("http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=102405376&FILE_NO=19903148&FILE_NO_TYPE=TM_APPL&SOAPQC=0");
		String result = sparit.sendGet(conn);
		String[] s = new String[8];
		s[0] = "<td width=\"460\">[0-9]{0,8}";
		s[1] = "<b>状况：<br>Status:</b></td>[\\s]*<td>[\\s\\S]{0,25}</td>";
		s[2] = "<IMG src=\"[\\S]*\" border=\"0\" alt=\"image\">";
		s[3] = "<td nowrap><b>商标种类：<br>Mark Type:</b></td>[\\s]*<td>[\\s]*[\\S]*<br>";
		s[4] = "D-M-Y\\)</b></td>[\\s]*<td>[\\S]{10}[\\s]*</td>";
		s[5] = "姓名／名称、地址：<br>[\\s\\S]*Address:[\\s\\S]{0,500}<td>&nbsp;</td>";
		s[6] = "for Service:</b></td>[\\s\\S]{0,350}<td>&nbsp;</td>";
		s[7] = "<a href=\"javascript:;\" onClick=\"location.replace\\('#class_no[0-9]{0,3}";
		Map<String, String> map = new HashMap<String, String>();
		map.put("application", s[0]);//
		map.put("status", s[1]);//
		map.put("mark", s[2]);//
		map.put("type", s[3]);
		map.put("date", s[4]);//
		map.put("nameAddress", s[5]);//
		map.put("service", s[6]);//
		map.put("classNO", s[7]);//
		
		Map<String, String> mmap = new HashMap<>();
		for (Map.Entry<String, String> ma : map.entrySet()) {
			r = Pattern.compile(ma.getValue());
			m = r.matcher(result);
			if(m.find()){
//				System.out.println(ma.getKey() + " : " +m.group());	
//				System.out.println();
				mmap.put(ma.getKey(), m.group());
			}
		}
		mark.setApplication(mmap.get("application"));
//		System.out.println(mmap.get("classNO"));
		mark.setClassNO(mmap.get("classNO"));
		mark.setDate(mmap.get("date"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mark.setMark(mmap.get("mark"));
		mark.setNameAddress(mmap.get("nameAddress"));
		mark.setService(mmap.get("service"));
		mark.setStatus(mmap.get("status"));
		mark.setType(mmap.get("type"));
		MarkDAO markd = new MarkDAO();
		markd.insert(mark);
		
		
	}

}
