package org.work.nutch.parse.html;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.parse.Outlink;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.Parser;
import org.apache.nutch.storage.ParseStatus;
import org.apache.nutch.storage.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlParser implements Parser {
	public static final Logger LOG = LoggerFactory
			.getLogger("org.employ.nutch.parse.html");
	private static Collection<WebPage.Field> FIELDS = new HashSet<WebPage.Field>();

	static {
		FIELDS.add(WebPage.Field.METADATA);
	}
	
	private Configuration conf;

	public Parse getParse(String url, WebPage page) {
		Parse parse = new Parse();
		parse.setParseStatus(new ParseStatus());
		parse.setOutlinks(new Outlink[0]);
		Map<Utf8, ByteBuffer> metadata = page.getMetadata();

		LOG.info("Parsing URL:[" + url + "] with ");
		
		return parse;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public Configuration getConf() {
		return this.conf;
	}

	@Override
	public Collection<WebPage.Field> getFields() {
		return FIELDS;
	}
}
