package com.elio.webspy.ipproxy;

import java.util.List;

import com.elio.webspy.domain.IP;

public abstract class IPFilter {
	
	public abstract  List<IP> filter();

}
