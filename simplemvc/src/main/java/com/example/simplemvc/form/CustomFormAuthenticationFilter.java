package com.example.simplemvc.form;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.support.JstlUtils;

import com.example.simplemvc.jwt.JwtUtil;
import com.example.simplemvc.jwt.TokenTO;
import com.example.simplemvc.model.User;

public class CustomFormAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String CHARACTER_ENCODING_UTF_8 = "UTF-8";
	private String jwtSecretKey;

	private UserDetailsService userDetailsService;

	public CustomFormAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher,
			UserDetailsService userDetailsService, String secretKey) {
		super(requiresAuthenticationRequestMatcher);
		this.userDetailsService = userDetailsService;
		this.jwtSecretKey = secretKey;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new AuthenticationServiceException("Invalid credentials");
		}

		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		// TODO validate username password
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, password,
				Arrays.asList(new SimpleGrantedAuthority("USER")));

		return this.getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {

		// response.sendRedirect("/oauth/authorize");

		// chain.doFilter(request, response);

		// response.setStatus(HttpStatus.OK.value());
		// response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		// response.setCharacterEncoding(CHARACTER_ENCODING_UTF_8);
		//
		// JSONObject jsonResponse = new JSONObject();
		//
		// UserDetails user = (UserDetails) authentication.getPrincipal();
		//
		// String username = user.getUsername();
		// // String jwtToken = JwtUtil.getToken(jwtSecretKey,
		// // buildTokenTO(userDetailsService.loadUserByUsername(username),
		// request));
		// String jwtToken = JwtUtil.getToken(jwtSecretKey, new TokenTO());
		// try {
		// jsonResponse.put(JwtUtil.JWT_TOKEN, jwtToken);
		// jsonResponse.put("message", "Login Successful");
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// response.getWriter().write(jsonResponse.toString());
		// response.setHeader(JwtUtil.HEADER_APP_SUBJECT, authentication.getName());

	}

	private TokenTO buildTokenTO(UserDetails user, HttpServletRequest request) {
		LocalDateTime ldt = LocalDateTime.now().plusMinutes(15);
		Date expirationDate = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		Map<String, Object> headerClaims = new HashMap<>();
		headerClaims.put("type", "JWT");

		TokenTO tokenTO = new TokenTO();
		tokenTO.setSubjectId(user.getUsername());
		tokenTO.setSubject(user.getUsername());
		tokenTO.setClientIpAddress(request.getRemoteAddr());
		tokenTO.setBrowserFingerprintDigest(request.getHeader(JwtUtil.USER_AGENT));
		tokenTO.setIssueDate(new Date());
		tokenTO.setIssuer(JwtUtil.AUTH_SERVICE_JWT);
		tokenTO.setExpirationDate(expirationDate);
		tokenTO.setRoles(getUserRoles(user));
		tokenTO.setHeaderClaims(headerClaims);

		return tokenTO;
	}

	private String getUserRoles(UserDetails user) {
		return String.join(",", user.getAuthorities().stream().map(r -> r.getAuthority()).collect(Collectors.toList()));
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(CHARACTER_ENCODING_UTF_8);
		JSONObject jsonResponse = new JSONObject();
		try {
			jsonResponse.put("message", "Invalid Credentials");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(jsonResponse.toString());
	}

}
