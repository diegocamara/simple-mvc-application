package com.example.simplemvc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private DefaultTokenServices tokenServices;

	@Value("${frontend.application.resource-id}")
	private String frontendApplicationRecourceId;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.antMatcher("/api/**") // ### Specify path pattern that need OAuth authentication(Bearer auth) and
									// authorization
				.authorizeRequests().antMatchers("/**").access("#oauth2.hasScope('write') "
						+ "and #oauth2.clientHasRole('ROLE_CLIENT') " + "and hasRole('USER')");

	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer config) {
		config.resourceId(frontendApplicationRecourceId).tokenServices(this.tokenServices);
	}

}
