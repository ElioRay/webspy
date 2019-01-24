package com.elio.webspy.ipproxy;

/**
 * 
 * @author elio-ray
 *
 */
public class IPFetcher {
	// 记录当前日志

	private Paraseable paraser;
	
    //传递解析器
	public void setParser(Paraseable paraser) {
		this.paraser = paraser;
	}
	
	
	public String parserHtml(String html) {
		this.paraser.paraseIP(html);
		
		return null;
	}

	

}
