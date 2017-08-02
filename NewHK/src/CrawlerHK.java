import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.dao.SparitMarkDAO;
import com.dao.SparitUrlDAO;
import com.dao.addUrlDAO;

public class CrawlerHK {//23        from 19280000 to 19307900  time out 2 times in 06.1 take 61 data  140000
	 
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		addUrlDAO  addURL = new addUrlDAO();
		String urlFirst = "";
		String urlSecend = "";
		boolean bf = true;
		ThreadPoolExecutor executorFirst = new ThreadPoolExecutor(10, 15, 300,
			       TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
			    new ThreadPoolExecutor.CallerRunsPolicy());
		ThreadPoolExecutor executorSecound = new ThreadPoolExecutor(3, 5, 300,
			       TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10),
			    new ThreadPoolExecutor.CallerRunsPolicy());
		Thread threadAddUrl = new Thread(addURL);
		//生成一级页面url
		threadAddUrl.start();
		Thread.currentThread().sleep(200);
		while(bf){
			if(addURL.getList().size() > 0){//63
				synchronized(addURL){
					urlFirst = addURL.getList().get(0);
					addURL.removeList(urlFirst);
				}
				//生成二级页面url
				executorFirst.execute(new SparitUrlDAO(urlFirst));
//				Thread.currentThread().sleep(10000);
				if(SparitUrlDAO.urls.size() > 0){
					synchronized(SparitUrlDAO.urls.get(0)){
						urlSecend = SparitUrlDAO.urls.get(0);
						SparitUrlDAO.removeURLBean(urlSecend);
					}
					System.out.println("     :"+urlSecend);
					executorSecound.execute(new SparitMarkDAO(urlSecend));
				}
			}//else bf = false;
		}
//		bf = true;
//		while(bf){
//			Thread.currentThread().sleep(200);
//			if(SparitUrlDAO.urls.size() > 0){
//				synchronized(SparitUrlDAO.urls.get(0)){
//					urlSecend = SparitUrlDAO.urls.get(0);
//					SparitUrlDAO.removeURLBean(urlSecend);
//				}
//				System.out.println("     :"+urlSecend);
//				executorSecound.execute(new SparitMarkDAO(urlSecend));
//			}else bf = false;
//		}
		executorFirst.shutdown();
		executorSecound.shutdown();
	}
}
