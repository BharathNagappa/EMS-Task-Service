package com.ems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	EMSAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	EMSRequestFilter requestFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/authenticate").permitAll().and().csrf().disable()
		.authorizeRequests()
		.antMatchers("/h2-console/**").permitAll()
		.anyRequest().authenticated()
		.and().
		exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).
		and().
		sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
;
		http.headers().frameOptions().disable();
		http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);

	}

}
