package com.ems;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ems.dataobject.User;
import com.ems.dataobject.Users;
import com.ems.service.UserService;
import com.ems.util.JWTTokenUtil;

@Component
public class EMSRequestFilter extends OncePerRequestFilter{

	@Autowired
	JWTTokenUtil jwtTokenUtil;
	
	@Autowired
	UserService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if(SecurityContextHolder.getContext().getAuthentication()==null)
		{
			String bearerToken =  request.getHeader("Authorization");
			if(bearerToken!=null && bearerToken.length()>0)
			{
				String token = bearerToken.substring(bearerToken.indexOf(" "),bearerToken.length()).trim();
				System.out.println(token);
				if(jwtTokenUtil.validateToken(token))
				{
					String userName = jwtTokenUtil.getUsernameFromToken(token);
					User user = userService.getUserByName(userName);
					Users myUser = new Users(user);
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(myUser, null, myUser.getAuthorities());
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
