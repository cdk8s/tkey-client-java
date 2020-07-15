package com.cdk8s.tkey.client.rest.pojo.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OauthUserAttribute implements Serializable {

	private static final long serialVersionUID = -5890673731362415002L;

	private String userId;
	private String username;
	private String nickname;
	private String realName;
	private String avatarUrl;
	private String email;
	private String mobilePhone;
	private String tenantId;
	private String userType;
	private Map<String, Object> userAttributeExt;

}
