package com.elio.webspy.ipproxy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.elio.webspy.db.MyRedis;
import com.elio.webspy.domain.IP;

public class IPExecutor {
	
	public static void execute(Object taskLock) {
		List<Thread> threads = new ArrayList<Thread>();
		Queue<String> urls = new LinkedList<String>();		
		List<IP> proxyList = new ArrayList<IP>();
		
		ProxyPool pool = new ProxyPool(proxyList);
		
		for(int i = 1; i < 10; i++) {
			urls.offer("https://www.kuaidaili.com/free/intr/" + i);
		}
		for(int i = 2; i <= 10; i++) {
			Thread thread = new  Thread(new IPGetterThread(urls, pool, taskLock));
			threads.add(thread);
			thread.setName("thread" + i);
			thread.start();
		}
		
		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		MyRedis myRedis = new MyRedis();
		synchronized(taskLock) {
			//通知IP消费线程--> 爬取网页内容
			taskLock.notifyAll();
		}
		myRedis.saveIPList(proxyList);
		System.out.println("程序运行结束，共获取ip" + proxyList.size());
	}
}
