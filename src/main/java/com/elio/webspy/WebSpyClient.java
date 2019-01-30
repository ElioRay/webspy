package com.elio.webspy;

import java.util.LinkedList;
import java.util.Queue;

public class WebSpyClient {
	public static void main(String[] args) {
		System.out.println("--------Task Begin--------");
		Object lock  = new Object();   
		//获取IP代理池任务队列
		Queue<String> taskList = new LinkedList<String>();
		
		initIPTask(taskList);
		
		System.out.println("--------Task End");
	}
	
	static void initIPTask(Queue<String> taskList) {
		String indexPage = "http://www.ttmeiju.vip/index.php/meiju/index/engename/Movie/p/";
		for(int i = 1; i <= 110; i++) {
			taskList.add(indexPage + i + ".html");
		}
	}
}
