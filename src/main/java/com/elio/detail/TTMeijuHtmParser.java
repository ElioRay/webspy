package com.elio.detail;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TTMeijuHtmParser implements IParsePageDetail{
	@Override
	public List<String>  parseDetailHtml(String html){
		List<String> infoList = new ArrayList<>();
		Document doc = Jsoup.parse(html);
		Element table_tbody = doc.select("table[class=latesttable]").first().selectFirst("tbody[id=seedlist]");
		if(table_tbody != null){
			Elements tr_items = table_tbody.select("tr");
			for(Element td_item : tr_items){
				infoList.add(td_item.text());
			}
		}
		return infoList;
		
	}

}
