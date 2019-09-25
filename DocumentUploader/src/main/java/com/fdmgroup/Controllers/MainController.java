package com.fdmgroup.Controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdmgroup.DocumentUploader.Account;
import com.fdmgroup.DocumentUploader.AccountService;
import com.fdmgroup.DocumentUploader.Document;
import com.fdmgroup.DocumentUploader.DocumentService;
import com.fdmgroup.DocumentUploader.ServiceLevel;
import com.fdmgroup.DocumentUploader.User;
import com.fdmgroup.DocumentUploader.UserService;
import com.fdmgroup.DocumentUploader.Validator;

@Controller
public class MainController {
	
	//Save the uploaded file to this folder
//	private static String UPLOADED_FOLDER = "C:\\Users\\yasir.Abdulsahib\\JavaEclipseWorkspace\\documentuploader\\DocumentUploader\\src\\main\\resources\\uploads\\";
//	private static String UPLOADED_FOLDER = "C:\\Users\\saul.tobias\\JavaEclipseWorkspace\\documentuploader\\documentUploader\\DocumentUploader\\src\\main\\resources\\uploads\\";
//	private static String UPLOADED_FOLDER = "C:\\Users\\tyler.simmons\\JavaEclipseWorkspace\\documentuploader\\DocumentUploader\\src\\main\\resources\\uploads\\";
	private static String d = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	String UPLOADED_FOLDER = d + File.separator;
//	private Path fileStorageLocation = Paths.get(UPLOADED_FOLDER);
    
    
	@Resource
	private ApplicationContext ctx;

	@Resource
	private UserService userService;

	@Resource
	private AccountService accountService;

	@Resource
	private DocumentService documentService;
	
	@Resource
	private Document document;

	@Resource
	private PasswordEncoder encoder;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/registerAccount")
	public String toRegisterAccount(ModelMap model) {
		Account account = ctx.getBean(Account.class);
		User user = ctx.getBean("Admin", User.class);

		model.addAttribute("account", account);
		model.addAttribute("user", user);

		return "registration";
	}

	@PostMapping("/confirmAccount")
	public String toConfirmationOfAccount(ModelMap model, @ModelAttribute("account") Account account,
			BindingResult resultAccount, @ModelAttribute("user") User user, BindingResult resultUser,
			HttpServletRequest req) {
		
		account.setServiceLevel(req.getParameter("service"));

		Set<User> userList = new HashSet<>();
		user.setRole("Admin");
		String passwordValidation = Validator.validatePassword(user.getPassword(), req.getParameter("cpassword"));
		if (passwordValidation != null) {
			model.addAttribute("badRegister", passwordValidation);
			return "registration";
		}
		if (userService.find(user.getEmail()) != null) {
			model.addAttribute("badRegister", "This email is already in use, try again.");
			return "registration";
		}
		if (accountService.find(account.getAccountName()) != null) {
			model.addAttribute("badRegister", "This account name is already in use, try again.");
			return "registration";
		}
		user.setPassword(encoder.encode(user.getPassword()));
		
		userList.add(user);
		account.setUsers(userList);
		user.setAccount(account);
		accountService.save(account);
		userService.save(user);
		model.addAttribute("account", account);
		model.addAttribute("user", user);
		return "confirmAccount";
	}

	@RequestMapping("/login")
	public String toLogin(ModelMap model) {
		User user = ctx.getBean("blankUser", User.class);
		model.addAttribute("user", user);
		return "login";
	}

	@PostMapping("/documentManagement")
	public String toDocumentManagement(ModelMap model, HttpServletRequest req, User user) {

		try {
			if (req.getParameter("email") != null) {
				user = userService.find(req.getParameter("email"));
				if (user == null) {
					model.addAttribute("badCreds", "Email not found please try again");
					return "login";
				}
			} else {
				user = (User) req.getSession().getAttribute("user");
			}

		} catch (NoSuchElementException nsee) {
			model.addAttribute("badCreds", "Email not found please try again");
			return "login";
		}
		if (encoder.matches(req.getParameter("password"), user.getPassword())) {
			model.addAttribute("user", user);
			HttpSession session = req.getSession();
			Account a = user.getAccount();
			model.addAttribute("documents", user.getAccount().getDocuments());
//			model.addAttribute("document", document);
			ServiceLevel service = ctx.getBean(a.getServiceLevel(), ServiceLevel.class);
			if (service.getAdvertisement()) {
				model.addAttribute("advertisement", "advertisement goes here");
				session.setAttribute("advertisement", "advertisement goes here");
			}
	        session.setAttribute("user", user);
	        session.setAttribute("account", a);
			return "documentManagement";
		}

		model.addAttribute("badCreds", "Incorrect password please try again. ");

		return "login";
	}

	@PostMapping("/upload")
	public String toConfirmDocumentUpload(@RequestParam("file") MultipartFile file, @SessionAttribute("user") User user, @SessionAttribute("account") Account account,
                                   RedirectAttributes redirectAttributes, ModelMap model, String fileDownloadUri, HttpSession session, Document document) {
		
		ServiceLevel service = ctx.getBean(account.getServiceLevel(), ServiceLevel.class);
		Set<Document> documents = documentService.getByAccount(account);
		
		if (service.getAdvertisement()) {
			model.addAttribute("advertisement", "advertisement goes here");
		}
		
		if (documents.size() >= service.getMaxUploads()) {
			model.addAttribute("maxUploads", "You have already reached the maximum uploads for your account service level.");
			model.addAttribute("documents", account.getDocuments());
			return "documentManagement";
		}
		
		user = (User) session.getAttribute("user"); 
		System.out.println(file.getOriginalFilename());
		if (file.isEmpty()) {
	            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
	            return "redirect:confirmUpload";
	        }

		try {
			if (account.getMonthlyUploads() < service.getMaxDocs() ) {
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			File dir = new File(UPLOADED_FOLDER + File.separator + user.getAccount().getAccountName() + File.separator);
			if (!dir.exists())
				dir.mkdirs();

			File uploadFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
			BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadFile));

			// add the download part:
			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(UPLOADED_FOLDER)
					.path(user.getAccount().getAccountName()).toUriString();
			System.out.println(fileDownloadUri);
			
			outputStream.write(bytes);
			outputStream.close();
			
			document.setDocName(file.getOriginalFilename());
			document.setAccount(user.getAccount());
			document.setDateSaved(LocalDate.now());
			document.setPath(fileDownloadUri);
			document.setDocType(file.getContentType());
			documentService.save(document);
			account.addDocument(document);
			accountService.save(account);

			
			model.addAttribute("fileDownloadUri", fileDownloadUri);
			model.addAttribute("filename", file.getOriginalFilename());
			model.addAttribute("file", file);
			model.addAttribute("documents", account.getDocuments());
//			model.addAttribute("document", document);
			model.addAttribute("user", user);
//			session.setAttribute("file", file);
			account.addToMonthlyUploads();
			}
			else {
				model.addAttribute("maxUploads", "You have already reached the maximum monthly uploads for your account service level.");
			}

		} catch (IOException e) {
	            e.printStackTrace();
	        }

	        return "documentManagement";
	}
	
	@RequestMapping("/uploads/{fileName:.+}")
	public ResponseEntity<org.springframework.core.io.Resource> downloadFile(MultipartFile file, HttpServletRequest request, User user) {
		user = (User) request.getSession().getAttribute("user");
		String contentType = null;
		org.springframework.core.io.Resource resource = null;
//		System.out.println("File: " + file.getOriginalFilename());
		try {
			Path filePath =  Paths.get(UPLOADED_FOLDER + user.getAccount().getAccountName() + File.separator + request.getParameter("docName"));
			resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				System.out.println("Resource exists");
			}
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			if(contentType == null) {
				contentType = "application/octet-stream";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	@PostMapping("deleteFile")
	public String deleteFile(MultipartFile file, HttpServletRequest request, @SessionAttribute("user") User user, @SessionAttribute("account") Account account, ModelMap model) {
		
		String advertisement = (String) request.getSession().getAttribute("advertisement");
		
		try {
			Path filePath =  Paths.get(UPLOADED_FOLDER + user.getAccount().getAccountName() + File.separator + request.getParameter("docName"));
			Files.delete(filePath);
			Document d = documentService.findByName(request.getParameter("docName"), account);
			account.removeDocument(d);
			documentService.removeByName(request.getParameter("docName"), account);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("documents", account.getDocuments());
		model.addAttribute("user", user);
		model.addAttribute("advertisement", advertisement);
		
		return "documentManagement";
	}


	@RequestMapping("/confirmUpload")
	public String toConfirmDocumentUpload(ModelMap model, HttpServletRequest req, MultipartFile file) {
		// model.addAttribute("File" , file);
		model.addAttribute("SuccessfulUpload", "successful Upload.....");
		return "confirmUpload";
	}

	@RequestMapping("/createUser")
	public String toCreateUser(ModelMap model, @SessionAttribute("account") Account account) {
		ServiceLevel service = ctx.getBean(account.getServiceLevel(), ServiceLevel.class);
		Set<User> users = userService.findByAccount(account);
		if (users.size() < service.getNumOfUsers() || users == null) {
			return "createUser";
		}
		model.addAttribute("badRegister", "Your account already has the max number of users associated. Cannot add more users.");
		return "updateAccount";
	}

	@PostMapping("/confirmUserUpdate")
	public String toConfirmUserUpdate(ModelMap model, HttpServletRequest req, @SessionAttribute("user") User user, @SessionAttribute("account") Account account, SessionStatus status) {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String question = req.getParameter("question");
		String answer = req.getParameter("answer");

		String passwordValidation = null;
		if (!encoder.matches(req.getParameter("currentPassword"), user.getPassword())) {
			model.addAttribute("badRegister", "You entered the incorrect current password, try again.");
			return "updateUser";
		}
		
		if (!name.equals("")) {
			user.setName(name);
		}
		if (!email.equals("")) {
			user.setEmail(email);
		} 
		if (!password.equals("")) {
			user.setPassword(encoder.encode(password));
			passwordValidation = Validator.validatePassword(req.getParameter("password"), req.getParameter("cpassword"));
		} 
		if (!question.equals("")) {
			user.setQuestion(question);
		} 
		if (!answer.equals("")) {
			user.setAnswer(answer);
		} 
		
		if (passwordValidation != null) {
			model.addAttribute("badRegister", passwordValidation);
			return "updateUser";
		}
		if (userService.find(user.getEmail()) != null) {
			model.addAttribute("badRegister", "This email is already in use, try again.");
			return "updateUser";
		}
		
		status.setComplete();
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
		session.setAttribute("account", account);
	
		userService.save(user);
		return "confirmUserUpdate";
	}

	@RequestMapping("/updateAccount")
	public String toUpdateAccount(ModelMap model, @SessionAttribute("user") User user) {
		String role = user.getRole();
		if (role.startsWith("A")) {
			return "updateAccount";
		} else if (role.startsWith("U")) {
			return "updateUser";
		}
		return "documentManagement";
	}

	@PostMapping("/confirmAccountUpdate")
	public String toConfirmAccountUpdate(ModelMap model, HttpServletRequest req, @SessionAttribute("user") User user, @SessionAttribute("account") Account account, SessionStatus status) {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String question = req.getParameter("question");
		String answer = req.getParameter("answer");
		String accountName = req.getParameter("accountName");
		String service = req.getParameter("service");

		String passwordValidation = null;
		if (!encoder.matches(req.getParameter("currentPassword"), user.getPassword())) {
			model.addAttribute("badRegister", "You entered the incorrect current password, try again.");
			return "updateAccount";
		}
		
		if (!name.equals("")) {
			user.setName(name);
		}
		if (!email.equals("")) {
			user.setEmail(email);
		} 
		if (!password.equals("")) {
			user.setPassword(encoder.encode(password));
			passwordValidation = Validator.validatePassword(req.getParameter("password"), req.getParameter("cpassword"));
		} 
		if (!question.equals("")) {
			user.setQuestion(question);
		} 
		if (!answer.equals("")) {
			user.setAnswer(answer);
		} 
		if (!accountName.equals("")) {
			account.setAccountName(accountName);
		}
		if (!service.equals("")) {
			account.setServiceLevel(service);
		}
		
		if (passwordValidation != null) {
			model.addAttribute("badRegister", passwordValidation);
			return "updateAccount";
		}
		
		if (userService.find(req.getParameter("email")) != null) {
			model.addAttribute("badRegister", "This email is already in use, try again.");
			return "updateAccount";
		}
		if (accountService.find(req.getParameter("name")) != null) {
			model.addAttribute("badRegister", "This account name is already in use, try again.");
			return "updateAccount";
		}
		
		status.setComplete();
		HttpSession session = req.getSession();
		session.setAttribute("user", user);
		session.setAttribute("account", account);
		
		userService.save(user);
		accountService.save(account);
		return "confirmAccountUpdate";
	}

	@PostMapping("/confirmUser")
	public String toConfirmationOfUser(ModelMap model, HttpServletRequest req, @SessionAttribute("account") Account account, @SessionAttribute("user") User u, SessionStatus status) {
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String cpassword = req.getParameter("cpassword");
		String question = req.getParameter("question");
		String answer = req.getParameter("answer");
		String passwordValidation = Validator.validatePassword(password, cpassword);
		
		if (passwordValidation != null) {
			model.addAttribute("badRegister", passwordValidation);
			return "createUser";
		}
		
		if (userService.find(email) != null) {
			model.addAttribute("badRegister", "This email is already in use, try again.");
			return "createUser";
		}
		
		User user = ctx.getBean("blankUser", User.class);
		user.setAccount(account);
		
		if (!name.equals("")) {
			user.setName(name);
		}
		if (!email.equals("")) {
			user.setEmail(email);
		} 
		if (!password.equals("")) {
			user.setPassword(encoder.encode(password));
		} 
		if (!question.equals("")) {
			user.setQuestion(question);
		} 
		if (!answer.equals("")) {
			user.setAnswer(answer);
		} 
		userService.save(user);
		status.setComplete();
		HttpSession session = req.getSession();
		session.setAttribute("user", u);
		session.setAttribute("account", account);
		model.addAttribute("user", user);
		model.addAttribute("account", account);
		return "confirmUser";
	}

	@RequestMapping("/logout")
	public String toLogOut(SessionStatus status) {
		status.setComplete();
		return "redirect:/";
	}
}
