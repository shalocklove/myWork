package com.dao;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class urlDAO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread[] t = new Thread[10];
		//控制线程数
		int i = 0;
		String url = "";
		boolean b = true;
		addUrlDAO addUrl = new addUrlDAO();
		Thread daoAdd = new Thread(addUrl);
		daoAdd.start();
		while(b){
			if(addUrl.getList().size() >= 10){
				for(i = 0; i <= 9; i++){
					synchronized(addUrl){
						url = addUrl.getList().get(0);
						System.out.println("删除" + url);
						addUrl.removeList(url);
					}
					t[i] = new Thread(new SparitUrlDAO(url), String.valueOf(i));
					t[i].start();
					System.out.println(t[i].getName() + "开始" + i);
					System.out.println(url);
					
					b = false;
				}
			}
		}
		b = true;
		ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 150, 300,
			       TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(3),
			    new ThreadPoolExecutor.CallerRunsPolicy());
		while(b){
			for(i = 0; i < 10; i++){
				if(!t[i].isAlive()){
					if(addUrl.getList().size() > 0){
						synchronized(addUrl){
							url = addUrl.getList().get(0);
							System.out.println("删除" + url);
							addUrl.removeList(url);
						}
						System.out.println(url);
						executor.execute(new SparitUrlDAO(url));
					}
					else b=false;
				}
			}
		}
		executor.shutdown();
		
	}
}
