package com.example.simplemvc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.example.simplemvc.form.FormAuthenticationProvider;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private FormAuthenticationProvider formAuthenticationProvider;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.formAuthenticationProvider);
		// auth.inMemoryAuthentication().withUser("john").password("123").roles("USER");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.addFilterBefore(corsFilter(),
		// UsernamePasswordAuthenticationFilter.class);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic()
				.realmName("realmName").and().csrf().disable();
	}

	// @Bean
	// public CorsFilter corsFilter() {
	// CorsConfiguration configuration = new CorsConfiguration();
	// configuration.setAllowedOrigins(Arrays.asList("https://example.com"));
	// configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
	// "DELETE", "OPTIONS"));
	// configuration.setAllowedHeaders(Arrays.asList("authorization",
	// "content-type", "x-auth-token"));
	// configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
	// UrlBasedCorsConfigurationSource source = new
	// UrlBasedCorsConfigurationSource();
	// source.registerCorsConfiguration("/**", configuration);
	// return new CorsFilter(source);
	//
	// }

}
