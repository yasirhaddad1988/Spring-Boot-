package com.fdmgroup.DocumentUploader;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

	@Query("SELECT d FROM Document d WHERE d.account = ?1")
	public Set<Document> getByAccount(Account account);

	@Modifying
	@Transactional
	@Query("DELETE FROM Document d WHERE d.docName = ?1 AND d.account = ?2")
	public void removeByName(String docName, Account account);

	@Query("SELECT d FROM Document d WHERE d.docName = ?1 AND d.account = ?2")
	public Document findByName(String docName, Account account);

}
