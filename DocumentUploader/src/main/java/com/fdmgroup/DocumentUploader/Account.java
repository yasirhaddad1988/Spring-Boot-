package com.fdmgroup.DocumentUploader;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component
@Scope("prototype")
@Table(name = "accounts")
public class Account {

	@Id
	@Column(name = "account_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
	@SequenceGenerator(name = "account_generator", sequenceName = "account_id_seq")
	private long accId;
	@Column(name = "account_name")
	private String accountName;
	private String serviceLevel;
	@OneToMany(mappedBy = "account", cascade = { CascadeType.REFRESH, CascadeType.MERGE })
	private Set<User> users;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = { CascadeType.REFRESH, CascadeType.MERGE})
	private Set<Document> documents;
	private int monthlyUploads;

	public Account() {
		monthlyUploads = 0;
	}

	public long getAccId() {
		return accId;
	}

	public void setAccId(long accId) {
		this.accId = accId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public Set<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	

	public int getMonthlyUploads() {
		return monthlyUploads;
	}

	public void setMonthlyUploads(int monthlyUploads) {
		this.monthlyUploads = monthlyUploads;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (accId ^ (accId >>> 32));
		result = prime * result + ((accountName == null) ? 0 : accountName.hashCode());
		result = prime * result + monthlyUploads;
		result = prime * result + ((serviceLevel == null) ? 0 : serviceLevel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accId != other.accId)
			return false;
		if (accountName == null) {
			if (other.accountName != null)
				return false;
		} else if (!accountName.equals(other.accountName))
			return false;
		if (monthlyUploads != other.monthlyUploads)
			return false;
		if (serviceLevel == null) {
			if (other.serviceLevel != null)
				return false;
		} else if (!serviceLevel.equals(other.serviceLevel))
			return false;
		return true;
	}

	public void addDocument(Document document) {
		documents.add(document);
		
	}

	public void removeDocument(Document d) {
		documents.remove(d);
		
	}

	
	public void addToMonthlyUploads() {
		monthlyUploads++;
	}

}
