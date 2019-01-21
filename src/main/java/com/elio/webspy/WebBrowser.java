package com.elio.webspy;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.elio.webspy.domain.IP;

public class WebBrowser {	
	
	//使用本机的ip进行网页的抓取
	public String getHtml(String url) {
		String pageContent = "";
		int httpStatus;
		HttpClient client = HttpClients.createDefault();
		CloseableHttpResponse resp  = null;
		RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(1000)
				.setSocketTimeout(3000).build();
		HttpGet httpGet = new HttpGet(url);
		// 模拟浏览器报文请求
		httpGet.setConfig(config);		
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;" +
                "q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Cache-Control", "no-cache");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Host", "www.xicidaili.com");
        httpGet.setHeader("Pragma", "no-cache");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 " +
                "(KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		try {
			resp = (CloseableHttpResponse) client.execute(httpGet);
			httpStatus = resp.getStatusLine().getStatusCode();
			if(httpStatus == 200) {
				pageContent = EntityUtils.toString(resp.getEntity(), "UTF-8");
			}else {
				return null;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {

		}
		return pageContent;
	}
	
	/**
	 * 
	 * @param url
	 * @param ip
	 * @return
	 * @Descritption
	 *  使用代ip信息爬去网页内容
	 */
	public String getHtml(String url, IP ip) {
		Logger.debug("当前IP信息" + ip.getIpAddress() + ";端口"+ ip.getPort()+ "使用次数" + ip.getUseCount() + "次");
		String html = null; //抓取到的网内内容
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse resp = null;
		HttpGet httpGet = new HttpGet(url);
		HttpHost host = new HttpHost(ip.getIpAddress(), ip.getPort());
		RequestConfig  requestConfig = RequestConfig.custom().setProxy(host)
				.setConnectionRequestTimeout(3000)
				.setSocketTimeout(3000).build();
		httpGet.setConfig(requestConfig);
		
		try {
			resp = httpClient.execute(httpGet);
			int statusCode = resp.getStatusLine().getStatusCode();
			
			if(statusCode == 200) {
				html = EntityUtils.toString(resp.getEntity(), "UTF-8");
				ip.addUseCount();
			}else {
				return "";
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(resp != null) {
					resp.close();
				}
				httpClient.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return html;
	}

}
