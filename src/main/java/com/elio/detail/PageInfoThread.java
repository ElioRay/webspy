package com.elio.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.elio.webspy.Logger;
import com.elio.webspy.WebBrowser;
import com.elio.webspy.db.MyRedis;
import com.elio.webspy.domain.IP;

public class PageInfoThread implements Runnable{
	private Object taskLock;
	private Queue<String> taskList;
	private List<String> pageInfoList;
	private int intervalTime = 200; 	//每个任务的间隔时间
	private IParsePageDetail parser;
	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public PageInfoThread(Object taskLock, Queue<String> taskList, List<String> pageInfo,IParsePageDetail parser ) {
		this.taskLock = taskLock;
		this.taskList = taskList;
		this.pageInfoList = pageInfo;
		this.parser = parser;
	}
	

	
	public boolean executeTask(IP ip, String url) {
		List<String> list = new ArrayList<>();
		String pageContent = "";
		WebBrowser browser = new WebBrowser();
		pageContent = browser.getHtml(url, ip);
		if(parser != null && pageContent != null){
			list = parser.parseDetailHtml(pageContent);
			synchronized(taskLock){
				pageInfoList.addAll(list);
				if(pageInfoList.size() > 20){
					try {
						readWriteLock.writeLock().lock();
						for(String str : pageInfoList){
							Logger.log(str);
						}
					}finally {
						readWriteLock.writeLock().unlock();
					}
				}
				System.out.println("Current List info:" + pageInfoList.size());
			}
			return true;
		}else {
			System.out.println("任务失败:"+url); 
			return false;
		}
	}
	
	@Override
	public void run() {
		String currentTaskUrl;
		boolean isNeedNewIP = true;
		MyRedis redis = new MyRedis();
		IP ip = null;
		while(true) {
			synchronized(taskLock) {
				if(taskList.size() > 0) {
					currentTaskUrl = taskList.poll();
					Logger.debug(Thread.currentThread().getName() +"---->CurrentTask: " + currentTaskUrl);
				}else {
					Logger.debug(Thread.currentThread().getName() + "任务队列已空，退出任务队列");
					break;
				}
				if(isNeedNewIP) {
					isNeedNewIP = false;
					
					ip = redis.getIP();
					System.out.println("regain ip" + ip.getIpAddress());
				}

				if(!executeTask(ip, currentTaskUrl)) {

					isNeedNewIP = true;
				}
				try {
					Thread.sleep(intervalTime);
					System.out.println("控制线程访问速度100ms；" + Thread.currentThread().getName() + "线程恢复执行" );
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
