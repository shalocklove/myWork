import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.util.Sparit;

public class d {
	private static Sparit sparit = new Sparit();
	private static String url = "http://ipsearch.ipd.gov.hk/trademark/jsp/ereg_schi.jsp?SAVED_CRI=&FROM_SEARCH_RESULT=0&ITEM_KEY=819214848&FILE_NO=19000108&FILE_NO_TYPE=REG_TM&SOAPQC=0";
	private String FILE_NO;
	private String key;
	private String Url;
	public static List<String> urls = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//ereg_main_schi.jsp?SAVED_CRI=&amp;FROM_SEARCH_RESULT=0&amp;ITEM_KEY=102405376&amp;FILE_NO=19903148&amp;FILE_NO_TYPE=TM_APPL&amp;SOAPQC=1\" target=\"body\"
		String s = "";
		s = sparit.sendGet(sparit.Connection(url));
		System.out.println(url);
		String patternOocd = "D-M-Y\\)[\\s]{0,}</b>";
		//</b></td>[\\s]*<td>[\\S]{10}[\\s]*</td>
		Pattern r = Pattern.compile(patternOocd);
		Matcher m = r.matcher(s);
		if(m.find()){
			System.out.println(m.group());
		}
		
	}

}
