package com.fdmgroup.DocumentUploader;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = "com.fdmgroup")
public class DocumentUploaderApplication {
	
	 @SuppressWarnings("unused")
	private int maxUploadSizeInMb = 10 * 1024 * 1024; // 10 MB

	public static void main(String[] args) {
		SpringApplication.run(DocumentUploaderApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean(name="Admin")
	public User adminUser() {
	User user=new User();
	user.setRole("Admin");
	return user;	
	}
	
	@Bean(name="blankUser")
	public User blankUser() {
		User user= new User();
		user.setRole("User");
		return user;
	}
	
	@Bean(name="Bronze") 
	public ServiceLevel bronze(){
		ServiceLevel bronze = new ServiceLevel();
		bronze.setMaxDocs(2);
		bronze.setMaxUploads(2);
		bronze.setNumOfUsers(1);
		bronze.setAdvertisement(true);
		return bronze;
	}
	
	@Bean(name="Silver") 
	public ServiceLevel silver(){
		ServiceLevel silver = new ServiceLevel();
		silver.setMaxDocs(10);
		silver.setMaxUploads(5);
		silver.setNumOfUsers(1);
		silver.setAdvertisement(true);
		return silver;
	}
	
	@Bean(name="Gold") 
	public ServiceLevel gold(){
		ServiceLevel gold = new ServiceLevel();
		gold.setMaxDocs(50);
		gold.setMaxUploads(20);
		gold.setNumOfUsers(2);
		gold.setAdvertisement(false);
		return gold;
	}
	
	@Bean(name="Unlimited") 
	public ServiceLevel unlimited(){
		ServiceLevel unlimited = new ServiceLevel();
		unlimited.setMaxDocs(-1);
		unlimited.setMaxUploads(-1);
		unlimited.setNumOfUsers(10);
		unlimited.setAdvertisement(false);
		return unlimited;
	}
	
	@Bean(name="Enterprise") 
	public ServiceLevel enterprise(){
		ServiceLevel enterprise = new ServiceLevel();
		enterprise.setMaxDocs(-1);
		enterprise.setMaxUploads(-1);
		enterprise.setNumOfUsers(200);
		enterprise.setAdvertisement(false);
		return enterprise;
	}

}
