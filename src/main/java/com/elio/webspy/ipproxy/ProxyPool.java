package com.elio.webspy.ipproxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.elio.webspy.Logger;
import com.elio.webspy.domain.IP;

/**
 * 
 * @author jinfeng
 * ProxyPool  每个获取ip线程代理池，每个线程获取到ip之后把ip放入到
 * ip代理池当中
 */
public class ProxyPool {	
	/**
	 * 非线程安全变量，用户每个线程完成解析html之后讲获取到的
	 * ip信息保存在这个List当中；
	 */
	//  开始使用代理池中的ip阈值
	private int beginUseProxyThreshold = 10;
	
	private List<IP> proxyList;  
	// 用户保护当非安全线程安全变量的安全读写
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();	
	
	// 用户手动停止当前线程
	private boolean isRunning = true;
	
	//计数器，用户记录当前任务一共抓取了多少数据
	private int maxCount;
	
	IPFilter ipFilter = new ResponseFilter();

	IPFetcher ipFetcher = new IPFetcher();
	IPParser parser = new IPParser();

	
	public ProxyPool(List<IP> proxyList) {
		this.proxyList = proxyList;
	}
	
	public boolean saveIP(Queue<String> urls, Object taskLock) {
		boolean flag = false;
		IP ip = null; // 程序开始不使用代理，使用本机ip获得第一批代理之后使用代理

		ipFetcher.setParser(parser);
		while(isRunning) {
			String url;
			// 当前ip代理池中的ip数量达到一程度开始使用ip代理
			readWriteLock.readLock().lock();
			if(proxyList.size() >= beginUseProxyThreshold) {
				if(ip == null) {
					ip = proxyList.get((int) Math.random()*proxyList.size());
				}else if(ip.getUseCount() > 10) {
					ip = proxyList.get((int) Math.random()*proxyList.size());	
				}
			}
			readWriteLock.readLock().unlock();
			List<IP> ipList = new ArrayList<IP>();
			synchronized(taskLock) {
				 if(urls.isEmpty()) {
					 Logger.debug("当前任务队列已空---->" + Thread.currentThread().getName());
					 break;
				 }else {
					url = urls.poll();
				 }
			}
			
			if(!ipFetcher.parsrUrl(url, ip,ipList)) {
				//如果当前线程抓取队列的失败，把当前任务的url归还到任务队列，退出本次循序， 继续下一次循环
				synchronized(taskLock) {
					urls.offer(url); // 把失败的url重新返回任务队列当中
				}
				continue;
			}
			ipList = ipFilter.filter(ipList);
			
			readWriteLock.writeLock().lock();
			Logger.debug("线程" + Thread.currentThread().getName() + "进入合并区域"+ ipList.size());
			proxyList.addAll(ipList);
			Logger.debug("当前线程获取的ip成功保存！！！！;当前获取到的ip数量:" + proxyList.size());
			readWriteLock.writeLock().unlock();
		}
		return flag;	
	}

	public void stop(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
