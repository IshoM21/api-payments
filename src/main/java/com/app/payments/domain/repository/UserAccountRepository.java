package com.app.payments.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.payments.domain.model.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long>{

	Optional<UserAccount> findByEmail(String email);
	boolean existsByEmail(String email);
	
}
