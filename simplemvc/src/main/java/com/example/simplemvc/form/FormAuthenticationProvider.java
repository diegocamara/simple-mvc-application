package com.example.simplemvc.form;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class FormAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		// UserDetails user = userDetailsService.loadUserByUsername(username);
		// if (user == null || !password.equals(user.getPassword())) {
		// throw new AuthenticationCredentialsNotFoundException("Credentials don't
		// match");
		// }

		// return new UsernamePasswordAuthenticationToken(user.getUsername(),
		// user.getPassword(), user.getAuthorities());

		return new UsernamePasswordAuthenticationToken(username, password, null);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
