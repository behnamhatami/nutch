package org.employ.nutch.parse.html.parsers;

import java.util.HashMap;
import java.util.Map;

import org.employ.nutch.parse.html.EmployField;
import org.employ.nutch.parse.html.IParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class EEstekhdam implements IParser {

	@Override
	public Map<EmployField, String> parse(String content, String baseUri)
			throws Exception {

		Map<EmployField, String> parsedFields = new HashMap<EmployField, String>();

		Document doc = Jsoup.parse(content, baseUri);

		parsedFields.put(EmployField.title, doc.select("title").first().text());

		parsedFields.put(EmployField.content, doc.text());

		Element post = doc.select("div#main article").first();
		if (post != null) {
			if (!post.select("header h1.entry-title").isEmpty())
				parsedFields.put(EmployField.e_title,
						post.select("header h1.entry-title").first().text());

			if (!post.select("header time.updated").isEmpty())
				parsedFields.put(EmployField.e_date,
						post.select("header time.updated").first().text());

			if (!post.select("div.entry-content").isEmpty())
				parsedFields.put(EmployField.e_content,
						post.select("div.entry-content").first().text());
		}
		return parsedFields;

	}

}
