package com.fdmgroup.DocumentUploader;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

	public Optional<Account> findByAccountName(String accountName);

}
