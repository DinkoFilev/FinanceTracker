package com.budgetbeat.manager;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

import com.budgetbeat.dao.IUserDAO;
import com.budgetbeat.pojo.User;
import com.budgetbeat.pojo.UserMapper;


public class UserManager implements IUserDAO{


	private ConcurrentHashMap<String, String> registerredUsers; // email , password cache for users
	private ConcurrentHashMap<String, User> loggedUsers; // Logged users : email , UserObject
	
	private DataSource dataSource;
	   private JdbcTemplate jdbcTemplateObject;
	   @Override
	   public void setDataSource(DataSource dataSource) {
	      this.dataSource = dataSource;
	      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	   }
	   
	   public void maikaTi(){
		   System.out.println("mama ti");
	   }
	   
	   @Override
	   public void create(String firstName, String lastName, String email, String password) {
	      String SQL = "INSERT INTO users (first_name,last_name,email,password) values (?,?,?,?)";
	      
	      jdbcTemplateObject.update( SQL, firstName,lastName,email,password);
	      System.out.println("Created Record First Name = " + firstName + " Last Name = " + lastName+ " email = " + email+ " password = " + password);
	      return;
	   }
	   @Override
	   public User getUser(Integer id) {
	      String SQL = "SELECT * from users where user_id = ?";
	      User user = jdbcTemplateObject.queryForObject(SQL, 
	                        new Object[]{id}, new UserMapper());
	      return user;
	   }
	   @Override
	   public List<User> listUsers() {
	      String SQL = "SELECT * from users";
	      List <User> users = jdbcTemplateObject.query(SQL, 
	                                new UserMapper());
	      return users;
	   }

	   public void delete(Integer id){
	      String SQL = "DELETE from users where user_id = ?";
	      jdbcTemplateObject.update(SQL, id);
	      System.out.println("Deleted Record with ID = " + id );
	      return;
	   }
	   @Override
	   public void update(Integer userID ,String firstName, String lastName, String email, String password){
	      String SQL = "UPDATE users set first_name = ? ,last_name = ? , email = ? , password = ? where user_id = ?";
	      jdbcTemplateObject.update(SQL, userID,firstName, lastName,email,password);
	      System.out.println("Updated Record with ID = " + userID );
	      return;
	   }

	public String registerValidation(String firstName, String lastName, String email, String password) {
		
			if(!firstName.matches("^[a-zA-Z]{3,45}$")){
				System.out.println("NE MATCHVA firstName");
				return "firstName";
			}
			if(!lastName.matches("^[a-zA-Z]{3,45}$")){
				System.out.println("NE MATCHVA LastName");
			
				return "lastName";
			}
			if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
				System.out.println("NE MATCHVA emaila");
				return "email";
			}
			
			if(!password.matches("^[a-zA-Z0-9._-]{3,30}$")){
				System.out.println("NE MATCHVA parolata");
				return "password";
			}
			if (registerredUsers.containsKey(email)) {
				return "emailExist";
			}
			return "register";
		
	}
	
	
	private  void registerUser(String firstName, String lastName, String email, String password){
		//
		if(registerValidation(firstName, lastName, email, password).equals("register")){
			//INSERT USER TO DATABASE
			//add user to cache
		}
		
		
		

	}
	
	public void loggingUser(){
		
		
	}

	public boolean loginValidation(String username, String password) {
	
		return true;
	}

	private StringBuffer MD5Convert(String password) {
		MessageDigest md;
		StringBuffer sb = null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			sb = new StringBuffer();
			byte byteData[] = md.digest();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb;

	}

	

}
