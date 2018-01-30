package com.example.simplemvc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.simplemvc.form.CustomFormAuthenticationFilter;
import com.example.simplemvc.form.FormAuthenticationProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private FormAuthenticationProvider formAuthenticationProvider;

	@Value("${jwt.signkey}")
	private String jwtSignkey;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.formAuthenticationProvider);
		auth.inMemoryAuthentication().withUser("john").password("123").roles("USER");

	}

	protected CustomFormAuthenticationFilter getCustomAuthenticationFilter(String pattern) throws Exception {
		CustomFormAuthenticationFilter customAuthenticationFilter = new CustomFormAuthenticationFilter(
				new AntPathRequestMatcher(pattern, "POST"));
		customAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
		return customAuthenticationFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/simple/testing").permitAll().anyRequest().authenticated().and()
				.formLogin().permitAll().and().csrf().disable()
				// Optional form filter
				.addFilterBefore(getCustomAuthenticationFilter("/login"), UsernamePasswordAuthenticationFilter.class);
	}

}
