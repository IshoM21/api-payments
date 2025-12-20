package com.app.payments.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.payments.domain.model.UserAccount;
import com.app.payments.domain.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserAccount user = userAccountRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		return new CustomerUserDetails(user);
	}

}
