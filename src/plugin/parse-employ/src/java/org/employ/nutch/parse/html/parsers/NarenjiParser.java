package org.employ.nutch.parse.html.parsers;

import java.util.HashMap;
import java.util.Map;

import org.employ.nutch.parse.html.EmployField;
import org.employ.nutch.parse.html.IParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class NarenjiParser implements IParser {

	@Override
	public Map<EmployField, String> parse(String content, String baseUri)
			throws Exception {
		Map<EmployField, String> parsedFields = new HashMap<EmployField, String>();

		Document doc = Jsoup.parse(content, baseUri);

		parsedFields.put(EmployField.title, doc.select("title").first().text());

		parsedFields.put(EmployField.content, doc.text());

		Element post = doc.select("div.inner_content div.node").first();
		if (post != null) {
			parsedFields.put(EmployField.e_title, post.select("h1.title a")
					.first().text());

			parsedFields.put(EmployField.e_content, post
					.select("div.submitted").first().text());

			parsedFields.put(EmployField.e_date, post.select("p.content")
					.first().text());
		}
		return parsedFields;
	}

}
