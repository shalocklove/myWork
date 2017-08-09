import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dao2.Agency;
import com.util.Sparit;

public class demo {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		long a = System.currentTimeMillis();
		String result = "";
		String url = "http://www.xicidaili.com/";
		Sparit sparit = new Sparit();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 300,
			       TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
			    new ThreadPoolExecutor.CallerRunsPolicy());
		try {
			result = sparit.sendGet(sparit.Connection(url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pattern r = Pattern.compile("<td>[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}</td>[\\s]*<td>[0-9]{0,4}</td>");
		Matcher m = r.matcher(result);
		Matcher ip = null;
		Matcher post = null;
		String sip = "";
		String spost = "";
		while(m.find()){
			r = Pattern.compile("[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}");
			ip = r.matcher(m.group());
			r = Pattern.compile("<td>[0-9]{0,4}</td>");
			post = r.matcher(m.group());
			if(ip.find() && post.find()){
				sip = ip.group();
				spost = post.group().substring(4, post.group().length()-5);
				System.out.println("ip : " + sip + "   post : " + spost);
				executor.execute(new Agency(sip, Integer.parseInt(spost)));
			}
		}
		Thread.sleep(10000);
//		r = Pattern.compile("<td>[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}</td>[\\s]*<td>[0-9]{0,4}</td>");
//		try {
//			result = sparit.sendGet(sparit.Connection(url));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		m = r.matcher(result);
//		if(true){
//			System.out.println("yes");
//			while(m.find()){
//				
//				r = Pattern.compile("[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}\\.[0-9]{0,3}");
//				ip = r.matcher(m.group());
//				r = Pattern.compile("<td>[0-9]{0,4}</td>");
//				post = r.matcher(m.group());
//				if(ip.find() && post.find()){
//					sip = ip.group();
//					spost = post.group().substring(4, post.group().length()-5);
//					System.out.println("ip : " + sip + "   post : " + spost);
//					new Thread(new Agency(sip, Integer.parseInt(spost))).start();;
//				}
//			}
//		}
//		else System.out.println("no");
		Thread.sleep(1000);
		executor.shutdown();
		System.out.println(System.currentTimeMillis() - a + "HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		System.out.println(Agency.getProxs().size());
	}
}
