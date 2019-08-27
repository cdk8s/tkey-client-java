package com.cdk8s.tkey.client.rest.controller;

import com.cdk8s.tkey.client.rest.TkeyProperties;
import com.cdk8s.tkey.client.rest.pojo.bo.OAuth2AccessToken;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import com.cdk8s.tkey.client.rest.service.TkeyService;
import com.cdk8s.tkey.client.rest.utils.CodecUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
@Controller
public class TkeyController {

	@Autowired
	private TkeyService tkeyService;

	@Autowired
	private TkeyProperties tkeyProperties;

	//=====================================业务处理 start=====================================

	/**
	 * 接收 code，然后换取 token
	 */
	@SneakyThrows
	@RequestMapping(value = "/codeCallback", method = RequestMethod.GET)
	public void codeCallback(final HttpServletRequest request, final HttpServletResponse response, @RequestParam(value = "redirect_uri", required = true) String redirectUri) {
		String code = request.getParameter("code");

		if (StringUtils.isBlank(code)) {
			return;
		}

		getAccessToken(request, response, code);

		// 重定向到原请求地址
		redirectUri = CodecUtil.decodeURL(redirectUri);
		response.sendRedirect(redirectUri);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(final HttpServletRequest request, HttpServletResponse response) {
		String finalLogoutUri = tkeyProperties.getFinalLogoutUri();
		request.getSession().invalidate();
		return "redirect:" + finalLogoutUri;
	}

	@RequestMapping(value = "/logoutSuccess", method = RequestMethod.GET)
	@ResponseBody
	private String logoutSuccess() {
		return "登出成功";
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================

	private void getAccessToken(final HttpServletRequest request, final HttpServletResponse response, String code) {
		OAuth2AccessToken oauthToken = tkeyService.getAccessToken(code);
		String accessToken = oauthToken.getAccessToken();

		TkeyToken tkeyToken = new TkeyToken();
		tkeyToken.setAccessToken(accessToken);
		tkeyToken.setRefreshToken(oauthToken.getRefreshToken());
		tkeyToken.setAttributes(tkeyService.getUserProfile(oauthToken));

		HttpSession session = request.getSession();
		session.setAttribute("userinfo", tkeyToken);
	}

	//=====================================私有方法  end=====================================
}
