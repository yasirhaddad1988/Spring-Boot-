package com.fdmgroup.DocumentUploader;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.fdmgroup.Controllers.MainController;

import ch.qos.logback.core.Context;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {
	@Autowired
	private ApplicationContext ctx;
	@Autowired
	WebApplicationContext webCTX;
	@Autowired
	private MainController controller;
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;
	@Autowired
	private MockMvc mockmvc;
	@Mock
	private ModelMap model;
	@Mock
	private Account account;

	@Mock
	private User user;
	@Mock
	private SessionStatus status;

	@Mock
	private BindingResult resultAccount;
	@Mock
	private BindingResult resultUser;
	@Mock
	private HttpServletRequest req;
	@Mock
	private MultipartFile file;
	@Mock
	private Validator val;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockmvc = MockMvcBuilders.webAppContextSetup(webCTX).build();
	}

	@Test
	public void test_WhenStartingApplication_indexisReturened() throws Exception {
		mockmvc.perform(get("/")).andExpect(status().is(200)).andExpect(view().name("index"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/index.jsp"));

	}

	// roy test
	@Test
	public void test_WhenMovingToLoginPage_UserObjIsAddedToModel_LoginIsReturned() throws BeansException, Exception {
		mockmvc.perform(get("/login")).andExpect(status().is(200)).andExpect(view().name("login"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/login.jsp"));
	}

	@Test
	public void test_ControllerContextLoad() {
		assertThat(controller).isNotNull();

	}

	@Test
	public void test_ShouldReturnIndex() throws Exception {

		String testResult = controller.index();
		String testControl = "index";
		assertEquals(testControl, testResult);
	}

	@Test
	public void test_ShouldReturnregisterAccount() throws Exception {
		String testResult = controller.toRegisterAccount(model);
		String testControl = "registration";
		assertEquals(testControl, testResult);
	}

	@Test
	public void test_toLogin() {
		String Result = controller.toLogin(model);
		String testControl = "login";
		assertEquals(testControl, Result);
	}

	@Test
	public void test_toLogOut() {

		String Result = controller.toLogOut(status);
		String testControl = "redirect:/";
		assertEquals(testControl, Result);
	}

	@Test
	public void test_ToConfirmDocumentUpload() {

		String Result = controller.toConfirmDocumentUpload(model, req, file);
		String testControl = "confirmUpload";
		assertEquals(testControl, Result);
	}

	@Test
	public void test_toUpdateAccountAdim() {
		User user1 = new User();
		user1.setRole("Admin");
		String Result = controller.toUpdateAccount(model, user1);
		String testControl = "updateAccount";
		assertEquals(testControl, Result);
	}

	@Test
	public void test_toUpdateAccountUser() {
		User user1 = new User();
		user1.setRole("User");
		String Result = controller.toUpdateAccount(model, user1);
		String testControl = "updateUser";
		assertEquals(testControl, Result);
	}

	 @Test
	 public void test_toUpdateUser() {
	
	 Account account1 = new Account();
	 account1.setServiceLevel("Bronze");
	 account1.setAccountName("accName");
	 User user2 = new User();
	 user2.setName("user");
	 Set<User> users = new HashSet<>();
	 users.add(user2);
	 account1.setUsers(users);
	 accountService.save(account1);
	 String Result = controller.toCreateUser(model, account1);
	 String testControl = "createUser";
	 assertEquals(testControl, Result);
	 }
	 
	@Test
	public void test_toCreateUser_TooManyUsers_GoToUpdateAccount() {

		Account account1 = new Account();
		account1.setServiceLevel("Bronze");
		account1.setAccountName("accName");
		accountService.save(account1);
		User user2 = new User();
		user2.setName("user");
		user2.setAccount(account1);
		userService.save(user2);
		Set<User> users = new HashSet<>();
		users.add(user2);
		account1.setUsers(users);
		accountService.save(account1);
		String Result = controller.toCreateUser(model, account1);
		String testControl = "updateAccount";
		assertEquals(testControl, Result);
	}

//	@Test
//	 public void test_toConfirmationOfUser() {
//		 ModelMap model = new ModelMap();
//		 Account account = new Account();
//		 User user = new User();
//	 user.setName("name");
//	 user.setAccount(account);
//	 user.setAnswer("answer");
//	 user.setEmail("email");
//	 user.setId(2L);
//	 user.setPassword("Password123");
//	 user.setQuestion("why");
//	 user.setRole("role");
//	 String Result = controller.toConfirmationOfUser(model, req, account, user,
//	 status);
//	 String testControl = "confirmUser";
//	 assertEquals(testControl , Result);
//	 }
	
	@Test
	public void test_toConfirmationOfUser_ReturnsCreateUser_InvalidPasswordProvided() throws Exception {
		String name = "fistName";
		String email = "email";
		String password = "password";
		String question = "question";
		String answer = "answer";
		
		Account account = new Account();
		User user = new User();


		mockmvc.perform(post("/confirmUser").sessionAttr("account", account).sessionAttr("user", user).param("name", name).param("email", email)
				.param("password", password).param("cpassword", password).param("question", question)
				.param("answer", answer))
				.andExpect(status().isOk()).andExpect(view().name("createUser"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/createUser.jsp"));
	}
	
	@Test
	public void test_toConfirmationOfUser_ReturnsCreateUser_EmailAlreadyInUse() throws Exception {
		String name = "fistName";
		String email = "email@email.com";
		String password = "Password1!";
		String question = "question";
		String answer = "answer";
		
		User user = new User();
		user = userService.find(2652L);
		Account account = accountService.find(2902L);
		
		mockmvc.perform(post("/confirmUser").sessionAttr("account", account).sessionAttr("user", user).param("name", name).param("email", email)
				.param("password", password).param("cpassword", password).param("question", question)
				.param("answer", answer))
				.andExpect(status().isOk()).andExpect(view().name("createUser"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/createUser.jsp"));
	}
	
	@Test
	public void test_toConfirmationOfUser() throws Exception {
		String name = "fistName";
		String email = "emailthathasnotbeenmaderandom";
		String password = "Password1!";
		String cpassword = "confirmationP";
		String question = "question";
		String answer = "answer";
		
		Account account = new Account();
		account = accountService.find(2902L);
		User user = new User();

		
		mockmvc.perform(post("/confirmUser").sessionAttr("account", account).sessionAttr("user", user).param("name", name).param("email", email)
				.param("password", password).param("cpassword", password).param("question", question)
				.param("answer", answer))
				.andExpect(status().isOk()).andExpect(view().name("confirmUser"))
				.andExpect(forwardedUrl("/WEB-INF/jsp/confirmUser.jsp"))
				.andExpect(model().attribute("user", user))
				.andExpect(model().attribute("account", account));
	}
	
	

	// @Test
	// public void test_ToUploadUser() {
	// String Result = controller.toUpdateUser(model, account);
	// String testControl = "updateAccount";
	// assertEquals(testControl, Result);
	// }
	//
	// @Test
	// public void test_toDocumentManagmentPage() {
	// User testUser = ctx.getBean("blankUser", User.class);
	// req.setAttribute("user", testUser);
	// String Result = controller.toDocumentManagement(model, req, user);
	// String testController = "documentManagment";
	// assertEquals(testController, Result);
	// }
	 @Test
	 public void test_toConfrmationOfAccount(){
	 User user = new User();
	 account.setServiceLevel("Silver");
	 user.setName("natenate");
	 user.setPassword("password");
	 String testResult = controller.toConfirmationOfAccount(model, account,
	 resultAccount, user, resultUser, req);
	 String testControl = "registration";
	 assertEquals(testControl, testResult);
	 }
	 @Test
	 public void test_toconfrmationofAccount_GoToConfirmAccount() throws Exception {
		 User user = ctx.getBean("user",User.class);
		 user.setEmail("newEmail");
		 user.setPassword("Password1");
		 user.setName("testingUser");
		 mockmvc.perform(post("/confirmAccount")).andExpect(status().is(200)).andExpect(view().name("confirmAccount"))
		 .andExpect(forwardedUrl("/WEB-INF/jsp/confirmAccount.jsp"))
		 .andExpect(model().attribute("user", user));
	 }
	// @Test
	// public void test_WhenUserLogin_GoToTheDocumenManagmentPage() throws
	// BeansException, Exception {
	// User u = ctx.getBean("blankUser", User.class);
	// u.setId(1L);
	// u.setAnswer("newAnswer");
	// u.setEmail("newemail");
	// u.setName("newname");
	// u.setPassword("newpassword");
	// model.addAttribute("user", u);
	//
	// mockmvc.perform(post("/documentManagement")).andExpect(status().is(200)).andExpect(view().name("documentManagement"))
	// .andExpect(forwardedUrl("/WEB-INF/jsp/documentManagement.jsp"))
	// .andExpect(model().attribute("user", u));
	// }

}
