package com.elio.webspy.ipproxy;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
public class IPGetterThread implements Runnable{
	private Queue<String>  taskQueue;
	private ProxyPool proxyPool;
	private Object taskLock;
	
	
	public IPGetterThread(Queue<String> urls, ProxyPool proxyPool, Object taskLock) {
		this.proxyPool = proxyPool;
		this.taskQueue = urls;
		this.taskLock = taskLock;
		
	}
	
	@Override
	public void run() {
		this.proxyPool.saveIP(taskQueue, taskLock);
	}

}
