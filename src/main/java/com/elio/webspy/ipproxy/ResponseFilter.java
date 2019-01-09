package com.elio.webspy.ipproxy;

import java.util.ArrayList;
import java.util.List;
import com.elio.webspy.domain.IP;

public class ResponseFilter extends IPFilter{
	
	private double maxResponseTime = 1L; // 设置默认最大的过滤的响应时间为秒	
	
	public List<IP> filter(List<IP> ipList){
		List<IP> newIPList = new ArrayList<IP>();
		for(IP ip : ipList) {
			if(ip.getResponseTime() < maxResponseTime) {
				newIPList.add(ip);
			}
		}
		return ipList;
	}

}
