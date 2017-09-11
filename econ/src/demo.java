import java.util.Scanner;

import com.Sparit.FirstSparit;
import com.Sparit.SecondSparit;
import com.Sparit.SparitMassage;
import com.Sparit.ThirdSparit;

public class demo {
	
	public static void main(String[] arg) throws InterruptedException{
		String csfcfc = "";
		FirstSparit fs = new FirstSparit();
		fs.doSomeThing();
		fs.setCaptcha(fs.cookies());
		System.out.println("postURL : " + fs.getPostUrl());
		String postUrl = fs.getPostUrl();
		System.out.println("cookie : " + fs.cookies());
		String cookie = fs.cookies();
		System.out.println("viewstate : " + fs.getViewState());
		String view = fs.getViewState();
		System.out.println("输入验证码");
		Scanner in = new Scanner(System.in);
		String captcha = in.nextLine();
		System.out.println("输入成功");
		Thread.sleep(3000);
		SecondSparit ss = new SecondSparit(fs.getPostUrl() + "p_p_id=MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1&_MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l__jsfBridgeAjax=true&_MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l__facesViewIdResource=/index.xhtml"
				, captcha, fs.cookies(), fs.getViewState());
		ss.sendPost();
		csfcfc = ss.getCsfcfc();
		Thread.sleep(3850);
		ThirdSparit ts = new ThirdSparit(fs.getPostUrl() + "p_p_id=MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1&_MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l__jsfBridgeAjax=true&_MarcaQueryPortlet_WAR_MarcaQueryPortletportlet_INSTANCE_hYJ3XqEtu42l__facesViewIdResource=/index.xhtml"
				, captcha, fs.cookies(), fs.getViewState());
		ts.sendPost();
		Thread.sleep(6000);
		SparitMassage sm = new SparitMassage();
		sm.getResult(postUrl, cookie, view, ts.getCsfcfc());
	}
}


//10:23  10:33 15  10:50 26  34