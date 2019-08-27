package com.cdk8s.tkey.client.rest.pojo.bo;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OAuth2AccessToken implements Serializable {
	private static final long serialVersionUID = -2833689273468744284L;

	private String accessToken;
	private String refreshToken;

	private String tokenType;
	private Integer expiresIn;

	private String scope;

}
