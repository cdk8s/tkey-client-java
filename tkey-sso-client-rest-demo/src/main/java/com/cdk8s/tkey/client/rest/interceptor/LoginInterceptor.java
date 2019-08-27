package com.cdk8s.tkey.client.rest.interceptor;

import com.cdk8s.tkey.client.rest.TkeyProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TkeyProperties tkeyProperties;

	//=====================================业务处理 start=====================================

	@SneakyThrows
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) {
		HttpSession session = request.getSession();
		Object userinfo = session.getAttribute("userinfo");

		String redirectUrl = getRedirectUrl(request);
		if (null == userinfo) {
			if (tkeyProperties.getEnableCodeCallbackToFront()) {
				responseJson(response);
			} else {
				response.sendRedirect(redirectUrl);
			}
			return false;
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}

	//=====================================业务处理 end=====================================
	//=====================================私有方法 start=====================================


	private String getRedirectUrl(final HttpServletRequest request) {
		return tkeyProperties.getFinalRedirectUri(request);
	}

	@SneakyThrows
	private void responseJson(final HttpServletResponse response) {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		Map<String, Object> responseMap = new HashMap<>(4);
		responseMap.put("isSuccess", false);
		responseMap.put("msg", "您还未登录，请先登录");
		responseMap.put("timestamp", Instant.now().toEpochMilli());
		responseMap.put("code", "0");
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(responseMap);
		PrintWriter out = response.getWriter();
		out.print(json);
	}


	//=====================================私有方法  end=====================================


}
