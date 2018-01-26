package com.example.simplemvc.configuration;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.example.simplemvc.model.User;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		final Map<String, Object> additionalInfo = new HashMap<>();
		
		additionalInfo.put("organization", authentication.getName() + randomAlphabetic(4));

		User user = (User) authentication.getUserAuthentication().getPrincipal();

		additionalInfo.put("user", user);

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		return accessToken;
	}
}
