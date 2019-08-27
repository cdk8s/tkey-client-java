package com.cdk8s.tkey.client.rest.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.net.URLEncoder;


@Slf4j
public final class CodecUtil {

	//编码
	@SneakyThrows
	public static String encodeURL(String source) {
		return URLEncoder.encode(source, "UTF-8");
	}

	//解码
	@SneakyThrows
	public static String decodeURL(String source) {
		return URLDecoder.decode(source, "UTF-8");
	}

}
