package com.elio.detail;

import java.util.Queue;

public class DetailFetcherThread extends Thread{
	// 每个线程共享的任务队列
	private Queue<String> taskList;
	// 线程协同的
	private Object lock;
	private FetchDetail fetcher;
	
	public DetailFetcherThread(Queue<String> taskList, Object lock) {
		this.taskList = taskList;
		this.lock = lock;
		fetcher = new FetchDetail(taskList,lock);
	} 
	@Override
	public void run() {
		fetcher.execute();
	}
}
