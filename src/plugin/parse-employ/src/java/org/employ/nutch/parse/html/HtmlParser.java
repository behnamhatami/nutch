package org.employ.nutch.parse.html;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.apache.avro.util.Utf8;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.parse.Outlink;
import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.ParseStatusCodes;
import org.apache.nutch.parse.Parser;
import org.apache.nutch.storage.ParseStatus;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.util.Bytes;
import org.apache.nutch.util.NutchConfiguration;
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

	@Override
	public Parse getParse(String url, WebPage page) {
		Parse parse = new Parse();
		parse.setParseStatus(new ParseStatus());
		parse.setOutlinks(new Outlink[0]);

		LOG.info("Employ Parsing URL:[" + url + "]");

		IParser parser = ParserFactory.getParser(url);
		if (parser != null) {
			try {
				LOG.info("Parsing");
				Map<EmployField, String> parsedFields = parser.parse(
						Bytes.toString(Bytes.toBytes(page.getContent())), url);

				parse.setText(parsedFields.get(EmployField.content));
				parse.setTitle(parsedFields.get(EmployField.title));

				// set the metadata back into the page
				for (EmployField key : parsedFields.keySet())
					if (key != EmployField.content && key != EmployField.title)
						page.putToMetadata(new Utf8(key.name()), ByteBuffer
								.wrap(parsedFields.get(key).getBytes()));

				parse.getParseStatus().setMajorCode(ParseStatusCodes.SUCCESS);
			} catch (Exception e) {

				LOG.warn("Parse of URL: " + url + " failed", e);
				LOG.warn("content=["
						+ Bytes.toString(Bytes.toBytes(page.getContent()))
						+ "]");
				parse.getParseStatus().setMajorCode(ParseStatusCodes.FAILED);
			}
		}

		return null;
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

	public static void main(String[] args) throws IOException {
		String name = args[0];
		String url = "file:" + name;
		File file = new File(name);
		byte[] bytes = new byte[(int) file.length()];
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		in.readFully(bytes);
		Configuration conf = NutchConfiguration.create();
		HtmlParser parser = new HtmlParser();
		parser.setConf(conf);
		WebPage page = new WebPage();
		page.setBaseUrl(new Utf8(url));
		page.setContent(ByteBuffer.wrap(bytes));
		page.setContentType(new Utf8("text/html"));
		Parse parse = parser.getParse(url, page);
	}
}
