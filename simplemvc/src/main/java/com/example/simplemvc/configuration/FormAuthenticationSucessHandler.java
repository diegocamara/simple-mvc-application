package com.example.simplemvc.configuration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class FormAuthenticationSucessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Value("${jwt.signkey}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		OAuth2AccessToken oauth2Token = tokenServices.createAccessToken(convertAuthentication(authentication));

		String token = Jwts.builder().setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, this.secret.getBytes("UTF-8")).compact();

		response.setHeader("Authorization", "Bearer " + token);

		// String redirectUrl = new
		// StringBuilder(REDIRECT_PATH_BASE).append("?").append(FIELD_TOKEN).append("=")
		// .append(encode(oauth2Token.getValue())).append("&").append(FIELD_EXPIRATION_SECS).append("=")
		// .append(oauth2Token.getExpiresIn()).toString();

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
				"/oauth/authorize?response_type=token&client_id=sampleClientId&state=fzKTBBzJy2E3DIkRewmUrAqPtGvyDnbs9ItSCOtb&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2F&scope=read%20write");
		requestDispatcher.forward(request, response);
		// response.sendRedirect("http://localhost:8080/oauth/authorize?response_type=token&client_id=sampleClientId&state=fzKTBBzJy2E3DIkRewmUrAqPtGvyDnbs9ItSCOtb&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2F&scope=read%20write");

	}

	private OAuth2Authentication convertAuthentication(Authentication authentication) {
		OAuth2Request request = new OAuth2Request(null, "testingClientId", null, true, null, null, null, null, null);

		return new OAuth2Authentication(request,
				// Other option: new
				// UsernamePasswordAuthenticationToken(authentication.getPrincipal(), "N/A",
				// authorities)
				new PreAuthenticatedAuthenticationToken(authentication.getPrincipal(), "N/A"));
	}

	// private String encode(String in) {
	// String res = in;
	// try {
	// res = UriUtils.encode(in, GeneralConstants.ENCODING_UTF8);
	// } catch (UnsupportedEncodingException e) {
	// log.error("ERROR: unsupported encoding: " + GeneralConstants.ENCODING_UTF8,
	// e);
	// }
	// return res;
	// }

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + this.expiration * 1000);
	}

}
