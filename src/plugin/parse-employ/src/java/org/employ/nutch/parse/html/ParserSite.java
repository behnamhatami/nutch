package org.employ.nutch.parse.html;

import java.net.MalformedURLException;
import java.net.URL;

public enum ParserSite {
	Estekhdam("www.estekhtam.com"), Narenji("narenji.ir");
	
	private String host;

	private ParserSite(String host) {
		this.host = host;
	}

	public static ParserSite getSite(String url) {
		String host = null;

		try {
			host = new URL(url).getHost();
		} catch (MalformedURLException e) {
			return null;
		}

		for (ParserSite site : ParserSite.values())
			if (site.host.equals(host)) {
				System.out.println("Employ Parser Found.[" + site.name() + "]");
				return site;
			}

		System.out.println("Employ Parser Not Found.");
		return null;
	}
}
