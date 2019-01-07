package com.elio.webspy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	private static int LEVEL_DEBUG = 5;
	private static  int LEVEL_INFO = 3;
	private static int currentLevel = 5;
	private static String time;
	private static String location = "";
//	public Logger(Class clz) {
//		location = clz.getName();
//	}
	
	public Logger() {
		
	}
	
	public static void debug(String message) {
	    String level = " DEBUG_INFO:";
		if(currentLevel <= LEVEL_DEBUG) {
			log(level+message);
		}
	}
	
	public static  void log(String log) {
		String message;
		Date nowDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time = dateFormat.format(nowDate);
		message = location + "" + time + log;
		System.out.println(message);
		
	}
}
