package com.elio.webspy.domain;

import java.io.Serializable;

/**
 * @author elio-ray
 * @since 20190106
 * @Description 
 *  A class to describe basic ip information
 *  Bae on: {https://www.kuaidaili.com/free/}
 *
 */
/*
 *  when a class implements Serializable,There'll appear a warning notice, 
 *  It'means you have  compatible to later class version;
 *  you need to set a default value for later version when you delete/add/mofify
 *  class
 */

public class IP implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0L;
	private boolean isInit = false;
	private String ipAddress;
	private int useCount;
	private String type;
	private double responseTime;
	private String finalValidTime;
	private int port;
	public String getIpAddress() {
		return ipAddress;
	}
	
	public void addUseCount() {
		if(isInit) {
			useCount++;
		}else {
			useCount = 1;
			isInit = true;
		}
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public int getUseCount() {
		return useCount;
	}
	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(double responseTime) {
		this.responseTime = responseTime;
	}
	public String getFinalValidTime() {
		return finalValidTime;
	}
	public void setFinalValidTime(String finalValidTime) {
		this.finalValidTime = finalValidTime;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
}
