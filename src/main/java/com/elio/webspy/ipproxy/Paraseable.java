package com.elio.webspy.ipproxy;

import java.util.List;

import com.elio.webspy.domain.IP;

/**
 * 
 * @author elio-ray
 *
 */
public interface Paraseable {
	List<IP> paraseIP(String html);
}
