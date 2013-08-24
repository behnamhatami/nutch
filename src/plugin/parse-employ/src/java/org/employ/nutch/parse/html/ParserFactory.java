package org.employ.nutch.parse.html;

import java.util.HashMap;
import java.util.Map;

import org.employ.nutch.parse.html.parsers.EstekhdamParser;
import org.employ.nutch.parse.html.parsers.NarenjiParser;

public class ParserFactory {
	private static Map<ParserSite, IParser> processors = new HashMap<ParserSite, IParser>();

	static {
		processors.put(ParserSite.Estekhdam, new EstekhdamParser());
		processors.put(ParserSite.Narenji, new NarenjiParser());
	}

	public static IParser getParser(String url) {
		return processors.get(ParserSite.getSite(url));
	}
}
