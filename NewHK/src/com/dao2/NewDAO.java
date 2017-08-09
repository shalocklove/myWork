package com.dao2;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NewDAO implements Runnable {
	
	private SparitUrlDAO sparitDao = null;
	private String url = "";
	private List<String> urls;
	private SparitMarkDAO mark = null;
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 15, 20,TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(20));
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		sparitDao = new SparitUrlDAO(url);
		sparitDao.run();
		urls = sparitDao.getUrlList();
		for (String string : urls) {
			executor.execute(new SparitMarkDAO(string));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		executor.shutdown();
	}
	public NewDAO(String url){
		this.url = url;
	}

}
