package org.employ.nutch.parse.html;

import java.util.Map;

public interface IParser {
	Map<EmployField, String> parse(String content, String baseUri) throws Exception;
}
