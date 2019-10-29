package com.cdk8s.tkey.client.rest;

import com.cdk8s.tkey.client.rest.utils.CodecUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "tkey.sso.oauth.client")
public class TkeyProperties {

	private String clientId;
	private String clientSecret;
	private String tkeyServerUri;

	private Boolean enableCodeCallbackToFront = false;
	private String clientCodeCallbackUri;
	private String clientLogoutRedirectUri;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setTkeyServerUri(String tkeyServerUri) {
		this.tkeyServerUri = tkeyServerUri;
	}

	public String getTkeyServerUri() {
		return tkeyServerUri;
	}

	public String getClientCodeCallbackUri() {
		return clientCodeCallbackUri;
	}

	public void setClientCodeCallbackUri(String clientCodeCallbackUri) {
		this.clientCodeCallbackUri = clientCodeCallbackUri;
	}

	public String getClientLogoutRedirectUri() {
		return clientLogoutRedirectUri;
	}

	public void setClientLogoutRedirectUri(String clientLogoutRedirectUri) {
		this.clientLogoutRedirectUri = clientLogoutRedirectUri;
	}

	public Boolean getEnableCodeCallbackToFront() {
		return enableCodeCallbackToFront;
	}

	public void setEnableCodeCallbackToFront(Boolean enableCodeCallbackToFront) {
		this.enableCodeCallbackToFront = enableCodeCallbackToFront;
	}

	// ======================================================

	public String getFinalLogoutUri() {
		return getFinalLogoutUri(clientLogoutRedirectUri);
	}

	public String getFinalLogoutUri(String redirectUri) {
		if (StringUtils.isBlank(redirectUri)) {
			redirectUri = clientLogoutRedirectUri;
		}
		return getTkeyServerLogoutUri() + "?redirect_uri=" + redirectUri;
	}

	public String getFinalRedirectUri(javax.servlet.http.HttpServletRequest request) {
		return getFinalRedirectUri(request, false);
	}

	public String getFinalRedirectUri(javax.servlet.http.HttpServletRequest request, Boolean useReferer) {
		String sourceRequestURL = request.getRequestURL().toString();
		String queryParam = request.getQueryString();
		if (StringUtils.isNotBlank(queryParam)) {
			sourceRequestURL = sourceRequestURL + "?" + queryParam;
		}

		if (useReferer) {
			String refererUrl = request.getHeader("referer");
			if (StringUtils.isNotBlank(refererUrl)) {
				sourceRequestURL = refererUrl;
			}
		}

		sourceRequestURL = CodecUtil.encodeURL(sourceRequestURL);
		clientCodeCallbackUri = StringUtils.removeEnd(clientCodeCallbackUri, "/");
		sourceRequestURL = CodecUtil.encodeURL(clientCodeCallbackUri + "?redirect_uri=" + sourceRequestURL);
		return tkeyServerUri + "/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + sourceRequestURL;
	}

	public String getTkeyServerLogoutUri() {
		return tkeyServerUri + "/oauth/logout";
	}

	public String getAccessTokenUri() {
		return tkeyServerUri + "/oauth/token";
	}

	public String getUserInfoUri() {
		return tkeyServerUri + "/oauth/userinfo";
	}

	public String getAuthorizeUri() {
		return tkeyServerUri + "/oauth/authorize";
	}

}
