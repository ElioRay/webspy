package com.elio.webspy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.elio.detail.TaskExcutor;
import com.elio.webspy.ipproxy.IPExecutor;

/*
 * @author leijf 
 * @since 20190119
 * @Description 
 *  爬虫的主要函数入口
 */
public class App{
	public static void main(String[] args) {
	
		Object taskLock = new Object(); //.用户IP生产者和IP小飞的对象锁
		//主要的任务队列
		Queue<String>  mainTaskList = new  LinkedList<String>();
		// 初始化任务队列
		initList(mainTaskList);
		// 抓取IP
		IPExecutor.execute(taskLock);
		
		//把需要抓取的任务队列交给任务的执行者
		TaskExcutor taskExcutor = new TaskExcutor(mainTaskList,taskLock);
		
		taskExcutor.execute();
		
	}
	
	public static void initList(Queue<String> list) {
		String indexPage = "http://www.ttmeiju.vip/index.php/meiju/index/engename/Movie/p/";
		for(int i = 1; i <= 110; i++) {
			list.add(indexPage + i + ".html");
		}
	}
}
