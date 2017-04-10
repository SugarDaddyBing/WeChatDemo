package com.cruelbb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cruelbb.business.CheckUtil;
import com.cruelbb.business.MessageUtil;
import com.cruelbb.po.TextMessage;

public class WeChatServlet extends HttpServlet {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");

		PrintWriter out = resp.getWriter();
		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		PrintWriter out = resp.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(req);
			String FromUserName = map.get("FromUserName");
			String ToUserName = map.get("ToUserName");
			String MsgType = map.get("MsgType");
			String Content = map.get("Content");

			String message = null;
			if ("text".equals(MsgType)) {
				TextMessage text = new TextMessage();
				text.setFromUserName(ToUserName);
				text.setToUserName(FromUserName);
				text.setMsgType("text");
				text.setCreateTime(new Date().getTime());
				text.setContent("您发送的是" + Content);

				message = MessageUtil.textMessageToXml(text);

				System.out.println(message);
			}
			out.print(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
