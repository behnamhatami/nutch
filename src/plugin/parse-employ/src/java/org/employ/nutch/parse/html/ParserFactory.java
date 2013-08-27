package org.employ.nutch.parse.html;

import java.util.HashMap;
import java.util.Map;

import org.apache.nutch.fetcher.FetcherJob;
import org.apache.nutch.parse.ParserChecker;
import org.employ.nutch.parse.html.parsers.EEstekhdam;
import org.employ.nutch.parse.html.parsers.EstekhdamParser;
import org.employ.nutch.parse.html.parsers.NarenjiParser;

public class ParserFactory {
	private static Map<ParserSite, IParser> processors = new HashMap<ParserSite, IParser>();

	static {
		processors.put(ParserSite.Estekhdam, new EstekhdamParser());
		processors.put(ParserSite.Narenji, new NarenjiParser());
		processors.put(ParserSite.EEstekhdam, new EEstekhdam());
	}

	public static IParser getParser(String url) {
		return processors.get(ParserSite.getSite(url));
	}
}
