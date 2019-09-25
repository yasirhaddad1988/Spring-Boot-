package com.fdmgroup.DocumentUploader;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ServiceLevel {

	private int maxDocs;
	private int numOfUsers;
	private int maxUploads;
	private boolean advertisement;

	public ServiceLevel() {
	}

	public int getMaxDocs() {
		return maxDocs;
	}

	public void setMaxDocs(int maxDocs) {
		this.maxDocs = maxDocs;
	}

	public int getNumOfUsers() {
		return numOfUsers;
	}

	public void setNumOfUsers(int numOfUsers) {
		this.numOfUsers = numOfUsers;
	}

	public int getMaxUploads() {
		return maxUploads;
	}

	public void setMaxUploads(int maxUploads) {
		this.maxUploads = maxUploads;
	}

	public boolean getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(boolean advertisement) {
		this.advertisement = advertisement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (advertisement ? 1231 : 1237);
		result = prime * result + maxDocs;
		result = prime * result + maxUploads;
		result = prime * result + numOfUsers;
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
		ServiceLevel other = (ServiceLevel) obj;
		if (advertisement != other.advertisement)
			return false;
		if (maxDocs != other.maxDocs)
			return false;
		if (maxUploads != other.maxUploads)
			return false;
		if (numOfUsers != other.numOfUsers)
			return false;
		return true;
	}

}
