package com.cdk8s.tkey.client.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller
public class TestController {

	//=====================================业务处理 start=====================================

	/**
	 * 该地址需要认证，可用于测试认证过程
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	private Map user(final HttpServletRequest request) {
		HttpSession session = request.getSession();

		Map<String, Object> map = new HashMap<>();
		map.put("userinfo", session.getAttribute("userinfo"));
		return map;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	private String index() {
		return "这是首页，无需认证。请访问 /user";
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================


	//=====================================私有方法  end=====================================
}
