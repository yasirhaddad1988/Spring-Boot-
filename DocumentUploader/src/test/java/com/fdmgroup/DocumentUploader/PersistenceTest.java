package com.fdmgroup.DocumentUploader;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@WebAppConfiguration
public class PersistenceTest {
	
	@Autowired
	UserService userService;
	@Autowired
	AccountService accountService;
	@Autowired
	DocumentService documentService;
	@Autowired
	ApplicationContext ctx;
	
	@Autowired
	private PasswordEncoder encoder;

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test_AddUserToDatabase() {
		User user = ctx.getBean("blankUser", User.class);
		user.setName("name");
		user.setPassword("password");
		user.setEmail("email");
		user.setQuestion("question");
		user.setAnswer("Answer");
		Long test=1L;
		
		
		userService.save(user);
		
		assertEquals(test, userService.find(user.getId()).getId());
		assertEquals(user.getName(), userService.find(user.getId()).getName());
		assertEquals(user.getPassword(), userService.find(user.getId()).getPassword());
		assertEquals(user.getEmail(), userService.find(user.getId()).getEmail());
		assertEquals(user.getQuestion(), userService.find(user.getId()).getQuestion());
		assertEquals(user.getAnswer(), userService.find(user.getId()).getAnswer());
	}
	
	@Test
	public void test_AddUserToDatabaseWithAccount() {
		Set<User> userList= new HashSet<>();
		
		Account account = ctx.getBean(Account.class);
		account.setAccountName("accName");
		accountService.save(account);
		
		User user = ctx.getBean("blankUser", User.class);
		user.setName("newname");
		user.setPassword(encoder.encode("newpassword"));
		user.setEmail("newemail");
		user.setQuestion("newquestion");
		user.setAnswer("newAnswer");
//		user.setAccount(account);
		
		
		userList.add(user);
		account.setUsers(userList);
	
		userService.save(user);
		
		assertEquals(user, userService.find(user.getId()));
		
	}
	
	@Test
	public void test_AddAccountWithServiceLevel() {
		
		
		Account account = ctx.getBean(Account.class);
		account.setAccountName("accName");
		account.setServiceLevel("Bronze");
		accountService.save(account);
		
	
		accountService.save(account);
		
		assertEquals(account, accountService.find(account.getAccId()));
		
	}
	
	@Test
	public void test_AddDocumentToDatabase() {
		Document document = ctx.getBean(Document.class);
		
		documentService.save(document);
		
		assertEquals(document, documentService.find(document.getDocumentId()));
	}
	
	@Test
	public void test_DocumentGettersAndSetters() {
		LocalDate dateSaved = LocalDate.now();
		Account a = ctx.getBean(Account.class);
		Document document = new Document();
		document.setDocName("random");
		document.setDocType(".txt");
		document.setDateSaved(dateSaved);
		document.setPath("home/random.txt");
//		document.setAccount(a);
		
		documentService.save(document);
		Document d = documentService.find(document.getDocumentId());
		
		assertEquals("random", d.getDocName());
		assertEquals(".txt", d.getDocType());
		assertEquals(dateSaved, d.getDateSaved());
		assertEquals("home/random.txt", d.getPath());
//		assertEquals(a, d.getAccount());
		assertEquals(document.getDocumentId(), d.getDocumentId());
		assertEquals(document.hashCode(), d.hashCode());
		
	}

	@Test
	public void test_DocumentServiceReturnsNullIfFindingByNonExistentID() {
		Document document = documentService.find(999L);
		assertNull(document);
	}
	
	
	
}
