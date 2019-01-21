package com.elio.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class TaskExcutor {
	private Boolean isRunning = false;
	
	private Queue<String> taskList;
	
	private Object lock;

	private List<String> targetInfo;
	
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public TaskExcutor(Queue<String> taskList, Object taskLock) {
		this.taskList = taskList;
		this.lock = taskLock;
		this.targetInfo = new ArrayList<>();
	}
	
	public void execute() {
		// 创建线程执行线程
		List<Thread> threadList = new ArrayList<>();
		for(int i = 1; i < 5; i++) {
			Thread thread = new Thread(new PageInfoThread(lock, taskList, targetInfo));
			thread.setName("detail-thread" + i);
			threadList.add(thread);
			try {
				thread.join();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int i = 0; i < threadList.size(); i++) {
			Thread thread = threadList.get(i);
			thread.start();
		}
	}
}
