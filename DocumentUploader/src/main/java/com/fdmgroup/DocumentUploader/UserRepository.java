package com.fdmgroup.DocumentUploader;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public Optional<User> findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.account = ?1")
	public Set<User> findByAccount(Account account);

}
