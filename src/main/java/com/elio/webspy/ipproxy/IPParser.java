package com.elio.webspy.ipproxy;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.elio.webspy.Logger;
import com.elio.webspy.domain.IP;

/**
 * url:{https://www.kuaidaili.com/free/inha/}
 * 解析目标网址
 * @author elio-ray
 *
 */
public class IPParser implements Paraseable{
	@Override
	public List<IP> paraseIP(String html){
		List<IP> ipList = new ArrayList<>();
		Document doc = Jsoup.parse(html);
		Element table = doc.select("table[class=table table-bordered table-striped]").first();
		Logger.debug(table.text());
		Elements trs = table.select("td");
		for(Element tr : trs) {
			IP ip = new IP();
			Elements tds = tr.select("td");
			ip.setIpAddress(tds.get(0).text().toString());              //ip地址
			ip.setPort(Integer.parseInt(tds.get(1).text().toString())); //端口
			ip.setType(tds.get(2).text().toString()); 					// 设置网络类型（高匿）
			ip.setResponseTime(Double.parseDouble(tds.get(4).text().toString())); // 网络响应时间
			ip.setFinalValidTime(tds.get(5).text().toString());         //网络验证ip的最后时间
			ipList.add(ip);
		}
		return ipList;
	}
}
