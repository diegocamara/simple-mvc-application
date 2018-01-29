package com.example.simplemvc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private EntryPointUnauthorizedHandler authenticationEntryPoint;

	@Autowired
	private FormAuthenticationSucessHandler formAuthenticationSucessHandler;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(super.authenticationManagerBean());
		return authenticationTokenFilter;
	}

	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.inMemoryAuthentication().withUser("john").password("123").roles("USER").and().withUser("tom")
				.password("111").roles("ADMIN").and().withUser("user1").password("pass").roles("USER").and()
				.withUser("admin").password("nimda").roles("ADMIN");

	}// @formatter:on

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// http
		// // we don't need CSRF because our token is invulnerable
		// .csrf().disable()
		// // All urls must be authenticated (filter for token always fires (/api/**)
		// .authorizeRequests().antMatchers("/api/**").authenticated().and()
		// // Call our errorHandler if authentication/authorisation fails
		//// .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
		// // don't create session (REST)
		// .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//
		// // Custom JWT based security filter
		// http.addFilterBefore(authenticationTokenFilterBean(),
		// UsernamePasswordAuthenticationFilter.class);
		//
		// // disable page caching
		// http.headers().cacheControl();
		//
		// http.formLogin();

		http.formLogin().successHandler(formAuthenticationSucessHandler).and().httpBasic().disable().anonymous()
				.disable().authorizeRequests().anyRequest().authenticated();

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		// http.authorizeRequests().antMatchers("/auth/**").permitAll().anyRequest().authenticated().and().formLogin()
		// .loginPage("/login").permitAll();
		// http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

	}

}
