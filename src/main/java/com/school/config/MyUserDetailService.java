package com.school.config;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.school.entities.Users;
import com.school.repos.UserRepository;

@Service
public class MyUserDetailService implements UserDetailsService {

	private static final String LOG_STR = "MyUserDetailService.%s";

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Users user = repository.findByUserName(username);

		System.out.println(String.format(LOG_STR, "loadUserByUsername") + " , username = " + username);

		if (Objects.isNull(user)) {
			
			throw new UsernameNotFoundException("Incorrect username or password");

		}

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				new ArrayList<>());
	}

	public boolean isValidUser(String jwtToken, String username) {

		System.out.println(String.format(LOG_STR, "isValidUser")+", jwtToken = "+jwtToken + " , username = " + username);

		Users user = repository.findByUserNameAndToken(username,jwtToken);
		
		if(Objects.isNull(user)) {
			return false;
		}
		
		return user.getTokenExpirationDate().isAfter(LocalDateTime.now( ZoneId.of( "UTC" ) ).withNano( 0 )) && user.isLoggedIn() == true;

	}

}
