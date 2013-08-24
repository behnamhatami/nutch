package org.employ.nutch.indexer.usertags;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.avro.util.Utf8;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.nutch.indexer.IndexingException;
import org.apache.nutch.indexer.IndexingFilter;
import org.apache.nutch.indexer.NutchDocument;
import org.apache.nutch.storage.WebPage;
import org.apache.nutch.storage.WebPage.Field;
import org.apache.nutch.util.Bytes;
import org.apache.nutch.util.TableUtil;

public class UserTagIndexingFilter implements IndexingFilter {

	private static final Log LOG = LogFactory
			.getLog(UserTagIndexingFilter.class);
	private static final Set<WebPage.Field> FIELDS = new HashSet<WebPage.Field>();

	static {
		FIELDS.add(WebPage.Field.METADATA);
	}

	private Configuration conf;

	@Override
	public NutchDocument filter(NutchDocument doc, String url, WebPage page)
			throws IndexingException {
		LOG.info("Adding user tags for page:" + url);
		Map<Utf8, ByteBuffer> metadata = page.getMetadata();
		for (Utf8 key : metadata.keySet()) {
			String keyStr = TableUtil.toString(key);

			if (StringUtils.isEmpty(keyStr))
				continue;

			if (keyStr.startsWith("e_")) {
				String value = Bytes.toString(Bytes.toBytes(metadata.get(key)));
				doc.add(keyStr, value);
			}
		}
		return doc;
	}

	@Override
	public Configuration getConf() {
		return conf;
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	@Override
	public Collection<Field> getFields() {
		return FIELDS;
	}
}
