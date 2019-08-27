package com.cdk8s.tkey.client.rest.pojo.dto;

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
public class TkeyToken implements Serializable {

	private static final long serialVersionUID = 7975415790497139550L;

	private String accessToken;
	private String refreshToken;
	private OauthUserProfile attributes;

}
