package com.cruelbb.business;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.cruelbb.po.TextMessage;
import com.thoughtworks.xstream.XStream;

/**
 * xml - map
 *
 * @author wangbingyuan
 *
 */
public class MessageUtil {
	/**
	 * xml -> map
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */

	public static Map<String, String> xmlToMap(HttpServletRequest request)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();

		Document doc = reader.read(ins);

		Element root = doc.getRootElement();

		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();

		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}

		ins.close();
		return map;
	}

	/**
	 * text -> xml
	 *
	 * @param text
	 * @return
	 */
	public static String textMessageToXml(TextMessage text) {
		XStream xStream = new XStream();
		xStream.alias("xml", text.getClass());
		return xStream.toXML(text);

	}
}
