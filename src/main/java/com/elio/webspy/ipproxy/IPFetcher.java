package com.elio.webspy.ipproxy;

import java.util.List;

import com.elio.webspy.Logger;
import com.elio.webspy.WebBrowser;
import com.elio.webspy.domain.IP;

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
	
	
	public List<IP> parserHtml(String html) {
		return this.paraser.paraseIP(html);
		
	}

	/**
	 * 
	 * @param url
	 * @param ip
	 * @param ipList
	 * @return judge current job success
 	 */
	public boolean parsrUrl(String url, IP ip, List<IP> ipList) {

		boolean flag = false;
		String pageContent;
		WebBrowser webBrowser = new WebBrowser();
		
		if(ip  != null) {
			Logger.debug("线程： " + Thread.currentThread().getName() 
					+ "使用ip【" + ip.getIpAddress() + "】, port【" + ip.getPort() + "】" );
			ip.setUseCount(ip.getUseCount() + 1);
			pageContent = webBrowser.getHtml(url, ip);
		}else {
			pageContent = webBrowser.getHtml(url);
		}
		if(pageContent == null) {
			return false;
		}
		ipList.addAll(this.parserHtml(pageContent));
		return true;
		
	}
}
