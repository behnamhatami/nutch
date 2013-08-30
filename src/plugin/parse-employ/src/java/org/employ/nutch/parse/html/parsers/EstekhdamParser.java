package org.employ.nutch.parse.html.parsers;

import java.util.HashMap;
import java.util.Map;

import org.employ.nutch.parse.html.EmployField;
import org.employ.nutch.parse.html.IParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class EstekhdamParser implements IParser {

	@Override
	public Map<EmployField, String> parse(String content, String baseUri)
			throws Exception {
		Map<EmployField, String> parsedFields = new HashMap<EmployField, String>();

		Document doc = Jsoup.parse(content, baseUri);

		parsedFields.put(EmployField.title, doc.select("title").first().text());

		parsedFields.put(EmployField.content, doc.text());

		Element post = doc.select("div.post-inner").first();
		if (post != null) {
			if (!post.select("h1.post-title a").isEmpty())
				parsedFields.put(EmployField.e_title,
						post.select("h1.post-title a").first().text());

			if (!post.select("div.entry").isEmpty())
				parsedFields.put(EmployField.e_content, post
						.select("div.entry").first().text());

			if (!post.select("p.post-meta span.date span").isEmpty())
				parsedFields.put(EmployField.e_date,
						post.select("p.post-meta span.date span").first()
								.text());
		}
		return parsedFields;
	}
}
