package com.fdmgroup.DocumentUploader;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
	
	@Autowired
	private DocumentRepository documentRepository;

	public void save(Document document) {
		documentRepository.save(document);
	}

	public Document find(long documentId) {
		Optional<Document> document = documentRepository.findById(documentId);
		if(document.isPresent()) {
			return document.get();
		}
		return null;
	}

	public Set<Document> getByAccount(Account account) {
		Set<Document> documents = documentRepository.getByAccount(account);
		return documents;
	}

	public void removeByName(String docName, Account account) {
		documentRepository.removeByName(docName, account);
		
	}

	public Document findByName(String docName, Account account) {
		Document document = documentRepository.findByName(docName, account);
		return document;
	}

}
