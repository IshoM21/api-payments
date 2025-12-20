package com.app.payments.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.payments.domain.model.UserAccount;

public class CustomerUserDetails implements UserDetails{
	
	private final UserAccount user;
	
	public CustomerUserDetails(UserAccount user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return user.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.name()))
				.collect(Collectors.toSet());
	}


	@Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public UserAccount getUser() {
        return user;
    }

}
