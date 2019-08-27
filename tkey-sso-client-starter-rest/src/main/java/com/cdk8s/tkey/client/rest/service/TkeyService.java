package com.cdk8s.tkey.client.rest.service;


import com.cdk8s.tkey.client.rest.TkeyProperties;
import com.cdk8s.tkey.client.rest.pojo.bo.OAuth2AccessToken;
import com.cdk8s.tkey.client.rest.pojo.bo.OkHttpResponse;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserProfile;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class TkeyService {

	@Autowired
	private TkeyProperties tkeyProperties;

	@Autowired
	private OkHttpClient okHttpClient;

	//=====================================业务处理 start=====================================

	@SneakyThrows
	public OAuth2AccessToken getAccessToken(String code) {
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/x-www-form-urlencoded");

		Map<String, String> params = new HashMap<>();
		params.put("code", code);
		params.put("grant_type", "authorization_code");
		params.put("client_id", tkeyProperties.getClientId());
		params.put("client_secret", tkeyProperties.getClientSecret());
		params.put("redirect_uri", tkeyProperties.getClientCodeCallbackUri());

		OkHttpResponse okHttpResponse = post(tkeyProperties.getAccessTokenUri(), params, headers);
		if (okHttpResponse.getStatus() != HttpStatus.OK.value()) {
			throw new RuntimeException("获取 AccessToken 失败");
		}

		return getOAuth2AccessToken(okHttpResponse.getResponse());
	}

	@SneakyThrows
	public OauthUserProfile getUserProfile(OAuth2AccessToken oauthToken) {
		String url = tkeyProperties.getUserInfoUri() + "?access_token=" + oauthToken.getAccessToken();
		OkHttpResponse okHttpResponse = get(url);

		if (okHttpResponse.getStatus() != HttpStatus.OK.value()) {
			throw new RuntimeException("获取 UserInfo 失败");
		}

		return getOauthUserProfile(okHttpResponse.getResponse());
	}


	//=====================================业务处理 end=====================================

	//=====================================私有方法  start=====================================

	@SneakyThrows
	private OAuth2AccessToken getOAuth2AccessToken(String response) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		return mapper.readValue(response, OAuth2AccessToken.class);
	}

	@SneakyThrows
	private OauthUserProfile getOauthUserProfile(String response) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		return mapper.readValue(response, OauthUserProfile.class);
	}

	private OkHttpResponse get(String url) {
		Request request = new Request.Builder().url(url).build();
		return getResponse(request);
	}

	private OkHttpResponse post(String url, Map<String, String> params, Map<String, String> headers) {
		FormBody.Builder builder = new FormBody.Builder();
		if (params != null && params.keySet().size() > 0) {
			params.forEach(builder::add);
		}

		Request.Builder builderRequest = new Request.Builder();
		if (headers != null && headers.keySet().size() > 0) {
			headers.forEach((key, value) -> {
				builderRequest.addHeader(key, value);
			});
		}

		Request request = builderRequest.url(url).post(builder.build()).build();
		return getResponse(request);
	}

	private OkHttpResponse getResponse(Request request) {
		Response response = null;
		try {
			response = okHttpClient.newCall(request).execute();
			OkHttpResponse okHttpResponse = new OkHttpResponse();
			okHttpResponse.setStatus(response.code());
			okHttpResponse.setResponse(response.body().string());
			return okHttpResponse;
		} catch (Exception e) {
			log.error("okhttp error = {}", ExceptionUtils.getStackTrace(e));
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}


	//=====================================私有方法  end=====================================


}
