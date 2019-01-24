package com.elio.webspy.ipproxy;

import java.util.List;

import com.elio.webspy.domain.IP;

public class ResponseFilter extends IPFilter{
	
	private double maxResponseTime = 1L; // 设置默认最大的过滤的响应时间为秒	
	
	public List<IP> filter(){
		return null;
	}

}
