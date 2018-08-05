package com.csomet.springboot.app.model.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.csomet.springboot.app.model.dao.IUserDao;
import com.csomet.springboot.app.model.entity.Authority;
import com.csomet.springboot.app.model.entity.User;

@Service
public class JPAUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserDao userDato;
	
	private Logger logger = LoggerFactory.getLogger(JPAUserDetailsService.class);
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDato.findByUsername(username);
		
		if (user == null) {
			logger.error("Error: User is null");
			throw new UsernameNotFoundException("username: " + username + " does not exists!");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Authority auth : user.getAuthorities()) {
			authorities.add(new SimpleGrantedAuthority(auth.getAuthority()));
		}
		
		if (authorities.isEmpty()) {
			logger.error("Error: User has not roles assigned");
			throw new UsernameNotFoundException("username: " + username + " has not roles assigned !");
		}
		
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

}
