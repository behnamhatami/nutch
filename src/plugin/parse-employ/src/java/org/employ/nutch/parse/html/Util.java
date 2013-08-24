package org.employ.nutch.parse.html;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Element;

public class Util {
	private static final Log LOG = LogFactory.getLog(Util.class);

	// ///////// normalize incoming dates to ISO-8601 ///////////

	private static final String[] DATE_PATTERNS = new String[] { "yyyyMMdd",
			"MM/dd/yyyy", };
	private static final SimpleDateFormat ISO_8601_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSS");

	public static String convertToIso8601(String date) {
		try {
			Date d = DateUtils.parseDate(date, DATE_PATTERNS);
			return ISO_8601_DATE_FORMAT.format(d);
		} catch (Exception e) {
			LOG.warn("Cannot convert date: " + date + " to ISO-8601 format", e);
			return "";
		}
	}

	// /////////// get text content of XML //////////////////

	public static String getTextContent(Element root) {
		StringBuilder buf = new StringBuilder();
		getTextContent_r(buf, root);
		return buf.toString();
	}

	@SuppressWarnings("unchecked")
	private static void getTextContent_r(StringBuilder buf, Element e) {
		buf.append(e.getTextTrim());
		List<Element> children = e.getChildren();
		for (Element child : children) {
			getTextContent_r(buf, child);
		}
		buf.append(" ");
	}
}
