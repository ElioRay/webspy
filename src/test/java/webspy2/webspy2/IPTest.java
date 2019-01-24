package webspy2.webspy2;

import org.junit.Test;

import com.elio.webspy.Logger;
import com.elio.webspy.WebBrowser;
import com.elio.webspy.domain.IP;
import com.elio.webspy.ipproxy.IPFetcher;
import com.elio.webspy.ipproxy.IPParser;

public class IPTest {
	
	@Test
	public void testIPFetcher() {
		WebBrowser browser = new WebBrowser();
		String pageContent = browser.getHtml("https://www.kuaidaili.com/free/inha/1/");
		IPParser paraser = new IPParser();
		IPFetcher fetcher = new IPFetcher();
		fetcher.setParser(paraser);
		fetcher.parserHtml(pageContent);
	}
	
	@Test
	public void testWebBrowser() {
		
	}
}
