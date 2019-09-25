package com.fdmgroup.DocumentUploader;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository accountRepo;

	public void save(Account account) {
		accountRepo.save(account);
	}

	public Account find(long accId) {
		Optional<Account> account = accountRepo.findById(accId);
		if(account.isPresent()) {					
			return account.get();
		}
		return null;
	}
	
	public Account find(String accountName) {
		Optional<Account> account = accountRepo.findByAccountName(accountName);
		if(account.isPresent()) {
			return account.get();
		}
		return null;
	}
	
	

}
