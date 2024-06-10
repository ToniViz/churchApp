package com.app.church.church;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.InvalidAttributeValueException;
import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.app.church.church.dto.UserDTO;
import com.app.church.church.entities.articles.Article;
import com.app.church.church.entities.articles.Comment;
import com.app.church.church.entities.articles.Content;
import com.app.church.church.entities.sections.Course;
import com.app.church.church.entities.sections.Inscription;

import com.app.church.church.entities.users.Login;
import com.app.church.church.entities.users.Mapping;
import com.app.church.church.entities.users.Message;

import com.app.church.church.entities.users.Role;

import com.app.church.church.entities.users.User;
import com.app.church.church.repository.ArticleRepository;
import com.app.church.church.repository.CommentRepository;
import com.app.church.church.repository.CourseRepository;
import com.app.church.church.repository.InscriptionRepository;
import com.app.church.church.repository.LoginRepository;
import com.app.church.church.repository.MappingRepository;
import com.app.church.church.repository.MessageRepository;
import com.app.church.church.repository.RoleRepository;
import com.app.church.church.repository.UserRepository;
import com.app.church.church.services.ArticleService;
import com.app.church.church.services.UserService;




@SpringBootApplication
public class ChurchApplication implements CommandLineRunner {

	@Autowired
	private ArticleRepository ar;


	@Autowired
	private UserRepository ur;

	@Autowired
	private RoleRepository rr;

	@Autowired
	private CommentRepository cr;

	@Autowired
	private CourseRepository cor;

	@Autowired
	private LoginRepository lr;

	@Autowired
	private InscriptionRepository inscriptionRepository;

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;

	@Autowired
	private MappingRepository mappingRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(ChurchApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		//addCourse();
		//deleteUserCourse();
		//deleteCourse();
		//addComment();
		//deleteCommentWithUser();
		//deleteComment();
		//addUserAndArticle();
		//userLogin();
		//deleteUserLogin();
		//addInscription();
		//inscriptionMethodRepository();
		//addCourseAndInscription();
		//addUser();
		//dataTest();
	}

	@Transactional
	public void dataTest(){

		//First step
		//Add roles
		 /* 
		Role role = new Role("ROLE_USER");
		Role role2 = new Role("ROLE_ADMIN");
		Role role3 = new Role("ROLE_TEACHER");
		Role role4 = new Role("ROLE_PREACHER");
		this.rr.saveAll(Arrays.asList(role, role2, role3, role4));
		

		//Article 
		String genericA = "/api/article/v1/";
		//GET
		String articlePage = "";
		String articleChecked = "un-checked";
		String articleTopic = "{topic}";
		String getArt = "{id}";
		//POST
		String addArticle = "add-article/{id}";
		String addContent = "add-content/{id}";
		String addComent = "add-comment/{idArticle}/{idUser}";
		//PUT
		String articleVideo = "video/{id}";
		String articleMulti = "addMultimedia/{id}/{tipe}";
		String contentUpdate = "update-content/{id}";
		String articleUpdate = "update-article/{id}";
		//DELETE
		String deleteArticle = "delete-article/{id}";
		String deleteContent = "delete-content/{idArticle}/{idContent}";
		String deleteComment = "delete-comment/{id}";


		//Add endpoint to each role
		
		this.mappingRepository.saveAll(Arrays.asList(new Mapping("GET", genericA.concat(getArt)),
		new Mapping("POST", genericA.concat(addArticle)), new Mapping("DELETE", 
		genericA.concat(deleteArticle)), new Mapping("PUT", genericA.concat(articleUpdate))));
		*/
		/* 
		Role userR = this.rr.findByRole("ROLE_USER").get();
		Optional<Mapping> first = this.mappingRepository.findById(01L);
		if(first.isPresent()){
			System.out.println("Este es el mapping: " + first.get().getUrl());
		}else{
			System.out.println("NO EXISTE");
		}
		
		userR.getMappings().add(this.mappingRepository.findById(01L).get());
		this.rr.save(userR);

		Role adminR = this.rr.findByRole("ROLE_ADMIN").get();
		adminR.getMappings().add(this.mappingRepository.findById(02L).get());
		this.rr.save(adminR);

		Role preacherR = this.rr.findByRole("ROLE_PREACHER").get();
		preacherR.getMappings().add(this.mappingRepository.findById(03L).get());
		this.rr.save(preacherR);

		*/
		

		
		
		//Second step
		/*   
		User user = new Teacher("Juan", "Perez", "Calle pico", "0123456", "@corneta.com");
		Login login = new Login("Juan", passwordEncoder.encode("Juansito"));
		User user2 = new Supervisor("Pedro", "Perez", "Calle pico", "0123456", "@corneta.com");
		Login login2 = new Login("Pedro", passwordEncoder.encode("Pedrito"));
		User user3 = new Common("Lucas", "Perez", "Calle pico", "0123456", "@corneta.com");
		Login login3 = new Login("Lucas", passwordEncoder.encode("Luquitas"));
		User user4 = new Preacher("Andreas", "Perez", "Calle pico", "0123456", "@corneta.com");
		Login login4 = new Login("Andreas", passwordEncoder.encode("Andresito"));
		
		login.setUser(user);
		login2.setUser(user2);
		login3.setUser(user3);
		login4.setUser(user4);
		this.lr.saveAll(Arrays.asList(login, login2, login3, login4));
		*/
		
		/* 
		Login log1 = this.lr.findById(01L).get();
		log1.setPassword("Perezito");
		Login log2 = this.lr.findById(02L).get();
		log2.setPassword("Perezito");
		Login log3 = this.lr.findById(03L).get();
		log3.setPassword("Perezito");
		Login log4 = this.lr.findById(04L).get();
		log4.setPassword("Perezito");
		try {
			this.userService.updateLogin(log1, 01L);
			this.userService.updateLogin(log2, 02L);
			this.userService.updateLogin(log3, 03L);
			this.userService.updateLogin(log4, 04L);

		} catch (InvalidAttributeValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		 
		
		Role adminR = this.rr.findByRole("ROLE_ADMIN").get();
		Role teacherR = this.rr.findByRole("ROLE_TEACHER").get();
		Role preacherR = this.rr.findByRole("ROLE_PREACHER").get();
		Role userR = this.rr.findByRole("ROLE_USER").get();
		
		//Thrid step
		 
		//Add roles to Users
		try {
			this.userService.updateRole(teacherR, 05L);
			this.userService.updateRole(userR, 05L);
			this.userService.updateRole(adminR, 06L);
			this.userService.updateRole(userR, 06L);
			this.userService.updateRole(preacherR, 07L);
			this.userService.updateRole(userR, 07L);

		} catch (InvalidAttributeValueException e) {
			System.out.println("Error en la operación");
		}

		
		//User
		String genericU = "/api/user/v1/";
		//GET
		String getUser = "{id}";
		String getUEvents = "events/{id}";
		String getUMSender = "message-sender/{id}";
		String getUMReceiver = "message-receiver/{id}";
		String pageAll = "all-users";
		String pageAllType = "all-users-type/{id}";
		String getUCourses = "teacher/{id}";
		String getUserSimple = "user/{id}";
		//POST
		String addUser = "add-user";
		//PUT
		String updateUser = "update-user/{id}";
		String updatePhoto = "update-photo/{id}";
		String updateLogin = "update-login/{id}";
		String updateRole = "update-role/{id}";
		//DELETE
		String deleteUser = "delete/{id}";

		
	}

	public void deleteMessagesByConversation(){
		

		
		//Second part
		//this.messageRepository.deleteMessagesBetweenUsers(01L, 02L);
		List<Message> messages = (List<Message>) this.messageRepository.findAll();
		messages.forEach(m -> {
			System.out.println("El mensaje es:"+m.getTitle());
		});

		//tercero



	
	}


	@Transactional
	public void inscriptionMethodRepository(){
		Course course = new Course("Primero");
		this.cor.save(course);
		Course course2 = new Course("Segundo");
		this.cor.save(course2);
		Course course3 = new Course("Tercero");
		this.cor.save(course3);

		Inscription inscription = new Inscription();
		inscription.setCourse(course);
		this.inscriptionRepository.save(inscription);
		Inscription inscription2 = new Inscription();
		inscription2.setCourse(course);
		this.inscriptionRepository.save(inscription2);
		Inscription inscription3 = new Inscription();
		inscription3.setCourse(course3);
		this.inscriptionRepository.save(inscription3);


		List<Inscription> inscriptions = this.inscriptionRepository.findAllByCourse("Primero");
		inscriptions.forEach(ins -> {
			System.out.println("La inscripción es:"+ ins.getDate().toString());
		});


	}

	//Article test
	@Transactional
	public void addArticle(){

		//Guardando artículo
		Article article = new Article("de prueba", "Vamos a probar");
		Article articleSaved = this.ar.save(article);
		System.out.println(articleSaved.getTitle());

		
	} 


	@Transactional
	public void addContent(){
		Article art = null;
		Optional<Article> articulo = this.ar.findById(02L);
		if(articulo.isPresent()){
			art = articulo.get();
			Set<Content> contenido = new HashSet<>();
			contenido.add(new Content("contenido dos", "articulo dos"));
			contenido.add(new Content("contenido dos adicional", "articulo dos adicional"));
			art.setContent(contenido);
			this.ar.save(art);
		}

		
	}


	@Transactional
	public void deleteArticle(){
		Article art = null;
		Optional<Article> articulo = this.ar.findById(01L);
		if(articulo.isPresent()){
			art = articulo.get();
			this.ar.delete(art);
		}
	}


	@Transactional
	public void addUserAndArticle(){
		User preacher = new User("Juan", "Perez Mandela",
		 "Calle pico", "602421135", "juan@gmail.corneta");
		 this.ur.save(preacher);

		 Article article = new Article("de prueba", "Vamos a probar");
		Article articleSaved = this.ar.save(article);
		System.out.println(articleSaved.getTitle());

		 //Add article into user
		 Article art = null;
		Optional<Article> articulo = this.ar.findById(01L);
		if(articulo.isPresent()){
			art = articulo.get();	
			System.out.println(art.getTitle());
		}

		Optional<User> usuario = this.ur.findById(01L);
		if(usuario.isPresent()){
			User user = usuario.get();
			System.out.println("el nombre es:" + user.getName());
			Set<Article> articles = new HashSet<>();
			articles.add(art);
			user.setArticlesU(articles);
			this.ur.save(user);
		} 
		//Doing to delete the user from Article object
	/* 	Optional<Article> articulo = this.ar.findById(01L);
	 	articulo.get().getUsers().forEach(
			user -> {
				user.getArticlesU().remove(articulo.get());
				this.ur.save(user);
			}
		);
		articulo.get().getUsers().clear();

		this.ar.delete(articulo.get()); */
		

	}

	@Transactional
	public void deleteUser(){
		Optional<User> usuario = this.ur.findById(04L);
		if(usuario.isPresent()){
			User user = usuario.get();
			this.ur.delete(user);
		}
	}

	//Users test 

	@Transactional
	public void addUser(){
		
		 


		 Role role = new Role("ROLE_ADMIN");
		 this.rr.save(role);

		 Optional<Role> rol = this.rr.findById(01L);
		 if(rol.isPresent()){
			Role adminRol = rol.get();
			Optional<User> user = this.ur.findById(01L);
			if(user.isPresent()){
				System.out.println("El nombre de user es:"+user.get().getLogin().getUsername());
				user.get().getLogin().getRoles().add(adminRol);
				this.lr.save(user.get().getLogin());

			}
		 }


	}


	@Transactional
	public void deleteUserChecksRole(){
		Optional<User> adminU = this.ur.findById(05L);
			if(adminU.isPresent()){
				this.ur.delete(adminU.get());
			}
	}


	//Comments test

	@Transactional
	public void addComment(){
		Article article = new Article("Article test comments", "test para no partirnos de la risa y comer");
		this.ar.save(article);

		User preacher = new User("Juan", "Perez Mandela",
		 "Calle pico", "602421135", "juan@gmail.corneta");
		 this.ur.save(preacher);


		Article art = this.ar.findById(01L).get();
		

		Comment comment = new Comment("Los cuentos de hoffman");
		comment.setArticle(art);
		comment.setUsuario(preacher);
		System.out.println("EL ID DEL ARTICULO ES:"+ comment.getArticle().getId());
		this.cr.save(comment);
		

	}

	@Transactional
	public void deleteCommentWithUser(){
		Comment comment = this.cr.findById(01L).get();
		System.out.println("EL COMMENTARIO ES:"+ comment.getText());
		this.cr.delete(comment);
	}


	@Transactional
	public void deleteArtAndComment(){
		Optional<Article> article = this.ar.findById(01L);

		if(article.isPresent()){
			this.ar.delete(article.get());
		}
	}

	@Transactional
	public void deleteComment(){
		Comment comment = this.cr.findById(01L).get();
		this.cr.delete(comment);
	}


	//Courses
	//Las inscripciones se borran solamente desde el usuario
	@Transactional
	public void addInscription(){
		User preacher = new User("Juan", "Perez Mandela",
		 "Calle pico", "602421135", "juan@gmail.corneta");
		 this.ur.save(preacher);
		 User preacherTwo = new User("Juan", "Perez Mandela",
		 "Calle pico", "602421135", "juan@gmail.corneta");
		 this.ur.save(preacherTwo);
		 User common = new User("Pedro", "Piqueras", "Calle pico", "66546534651", "locura@");
		this.ur.save(common);

		//User user = this.ur.findById(01L).get();

		Course course = new Course("Locos");
		this.cor.save(course);

		/*Inscription inscription = new Inscription();
		inscription.setCourse(course);
		inscription.setUser(user);
		this.inscriptionRepository.save(inscription);

		User userTwo = this.ur.findById(02L).get();
		User userThree = this.ur.findById(03L).get();
		Course c = this.cor.findById(01L).get();
		
		
		this.ur.save(userTwo);
		this.ur.save(userThree);*/
		User user2 = this.ur.findById(01L).get();
		System.out.println("EL USUARIO DOS TIENE:" + user2.getInscription().getDate().toString());
		user2.setInscription(null);
		this.ur.save(user2);

	}
	
	@Transactional
	public void addCourseAndInscription(){
		/* 
		Course course = new Course("Nuevo curso");
		this.cor.save(course);

		Inscription inscription = new Inscription();
		inscription.setCourse(course);
		this.inscriptionRepository.save(inscription);

		Inscription inscription2 = new Inscription();
		inscription2.setCourse(course);
		this.inscriptionRepository.save(inscription2); */


		Course courseO = this.cor.findById(01L).get();
		this.cor.delete(courseO);
		

	}

	@Transactional
	public void deleteUserCourse(){
		User user = this.ur.findById(02L).get();
		this.ur.delete(user);
	}


	@Transactional
	public void deleteCourse(){
		
	}

	//user login

	@Transactional
	public void userLogin(){
		User user = this.ur.findById(01L).get();
		Login login = new Login("Federico", "pico");
		login.setUser(user);
		this.lr.save(login);

	}


	@Transactional
	public void deleteUserLogin(){
		User user = this.ur.findById(01L).get();
		this.ur.delete(user);
	}


	





	

}
