package com.example.simplemvc.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import com.example.simplemvc.form.FormAuthenticationProvider;

@Configuration
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
		// auth.authenticationProvider(this.formAuthenticationProvider);
		auth.inMemoryAuthentication().withUser("john").password("123").roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// http.addFilterBefore(corsFilter(),
		// UsernamePasswordAuthenticationFilter.class);
		// http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(
		// new WhiteListedAllowFromStrategy(Arrays.asList("http://localhost:4200"))));
		// http.headers().frameOptions().disable();
		http.authorizeRequests().antMatchers("/login", "/logout").permitAll().anyRequest().authenticated().and()
				.formLogin().permitAll().and().logout()
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
				.deleteCookies("JSESSIONID").invalidateHttpSession(true).and().csrf().disable();

	}

}
