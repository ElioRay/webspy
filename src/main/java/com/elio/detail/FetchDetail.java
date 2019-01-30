package com.elio.detail;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import com.elio.webspy.db.MyRedis;
import com.elio.webspy.domain.IP;

public class FetchDetail {
	Queue<String> taskList;
	IP ip = null;
	MyRedis redis = new MyRedis();
	private Object lock;
	String currentUrl;
	private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
	private ReadLock readLock = readWriteLock.readLock();
	private WriteLock writeLock = readWriteLock.writeLock();
	public FetchDetail(Queue<String> taskList2, Object lock) {
		this.taskList = taskList2;
		this.lock = lock;
	}
	public void execute() {
		while(!Thread.currentThread().isInterrupted()) {
			synchronized(lock) {
				if(taskList.size() > 0) {
					currentUrl = taskList.poll();
				}else {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(100);
				if(ip == null) {
					ip = redis.getIP();
				}
			}catch(InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + "--has Stop");
				Thread.currentThread().interrupt();
			}
		}
	}
	private void fetchInfo() {
		
	}
}
