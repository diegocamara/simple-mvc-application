package com.example.simplemvc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.simplemvc.form.CustomFormAuthenticationFilter;
import com.example.simplemvc.form.FormAuthenticationProvider;
import com.example.simplemvc.mediator.UserMediator;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private FormAuthenticationProvider formAuthenticationProvider;

	@Autowired
	private UserMediator userMediator;

	@Value("${jwt.signkey}")
	private String jwtSignkey;

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
		// auth.authenticationProvider(this.formAuthenticationProvider);
		// auth.userDetailsService(inMemoryUserDetailsManager());
		// auth.inMemoryAuthentication().withUser("john").password("123").roles("USER").and().withUser("tom")
		// .password("111").roles("ADMIN").and().withUser("user1").password("pass").roles("USER").and()
		// .withUser("admin").password("nimda").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("john").password("123").roles("USER");

	}// @formatter:on

	protected CustomFormAuthenticationFilter getCustomAuthenticationFilter(String pattern) throws Exception {
		CustomFormAuthenticationFilter customAuthenticationFilter = new CustomFormAuthenticationFilter(
				new AntPathRequestMatcher(pattern, "POST"), userMediator, jwtSignkey);
		customAuthenticationFilter.setAuthenticationManager(super.authenticationManager());
		return customAuthenticationFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.authorizeRequests().antMatchers("/simple/testing").permitAll().anyRequest().authenticated().and()
				.formLogin().successHandler(new RefererRedirectionAuthenticationSuccessHandler()).permitAll().and()
				.addFilterBefore(getCustomAuthenticationFilter("/login"), UsernamePasswordAuthenticationFilter.class);

	}

}
