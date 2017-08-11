import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dao2.Agency;
import com.dao2.AgencyDAO;
import com.mysql.jdbc.BlobFromLocator;
import com.util.Sparit;

public class demo {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
//		Sparit sparit = new Sparit();
//		String url = "http://www.xicidaili.com";
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 300,
//			       TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
//			    new ThreadPoolExecutor.CallerRunsPolicy());
//		try {
//			String result = sparit.sendGet(sparit.Connection(url));
//			System.out.println(result);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		AgencyDAO a = new AgencyDAO();
		a.run();
		for(;true;){
			Thread.sleep(10000);
			System.out.println(Agency.getProxs().size());
		}
	}
}
