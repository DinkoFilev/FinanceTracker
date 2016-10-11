package com.budgetbeat.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import com.budgetbeat.dao.IUserDAO;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;
import com.budgetbeat.pojo.UserMapper;

public class UserManager implements IUserDAO {

	private ConcurrentHashMap<String, String> registerredUsers = new ConcurrentHashMap<>(); // email ,  password cache for users
																																													
	private ConcurrentHashMap<String, User> loggedUsers = new ConcurrentHashMap<>(); // Logged users : email , UserObject
																						
	private DataSource dataSource;

	public void updateRegisterredUsers() { // bean init method
		if (registerredUsers.size() == 0) {
			System.out.println("EGO Q");
			String sql = "SELECT email,password from users";
			registerredUsers = (ConcurrentHashMap<String, String>) jdbcTemplateObject.query(sql, mapExtractor);

		}

	}

	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(String firstName, String lastName, String email, String password) {
		String SQL = "INSERT INTO users (first_name,last_name,email,password) values (?,?,?,?)";
		password = MD5Convert(password).toString();
		jdbcTemplateObject.update(SQL, firstName, lastName, email, password);
		System.out.println("Created Record First Name = " + firstName + " Last Name = " + lastName + " email = " + email
				+ " password = " + password);
		registerredUsers.put(email, password);
		return;
	}

	@Override
	public User getUserbyID(Integer id) {
		String SQL = "SELECT * from users where user_id = ?";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new UserMapper());
		return user;
	}

	@Override
	public User getUserbyEmail(String email) {
		String SQL = "SELECT * from users where email = ?";
		User user = jdbcTemplateObject.queryForObject(SQL, new Object[] { email }, new UserMapper());
		return user;
	}

	@Override
	public List<User> listUsers() {
		String SQL = "SELECT * from users";
		List<User> users = jdbcTemplateObject.query(SQL, new UserMapper());
		return users;
	}

	ResultSetExtractor mapExtractor = new ResultSetExtractor() {
		public Object extractData(ResultSet rs) throws SQLException {

			ConcurrentHashMap<String, String> mapOfKeys = new ConcurrentHashMap<String, String>();
			while (rs.next()) {
				String key = rs.getString("email");
				String obj = rs.getString("password");
				/* set the business object from the resultset */
				mapOfKeys.put(key, obj);
			}
			return mapOfKeys;
		}
	};

	public void delete(Integer id) {
		String SQL = "DELETE from users where user_id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Deleted Record with ID = " + id);
		return;
	}

	@Override
	public void update(Integer userID, String firstName, String lastName, String email, String password) {
		String SQL = "UPDATE users set first_name = ? ,last_name = ? , email = ? , password = ? where user_id = ?";
		jdbcTemplateObject.update(SQL, userID, firstName, lastName, email, password);
		System.out.println("Updated Record with ID = " + userID);
		return;
	}

	public String registerValidation(String firstName, String lastName, String email, String password) {

		if (!firstName.matches("^[a-zA-Z]{3,45}$")) {
			System.out.println("NE MATCHVA firstName");
			return "firstName";
		}
		if (!lastName.matches("^[a-zA-Z]{3,45}$")) {
			System.out.println("NE MATCHVA LastName");

			return "lastName";
		}
		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
			System.out.println("NE MATCHVA emaila");
			return "Email doesn't match the requirements";
		}

		if (!password.matches("^[a-zA-Z0-9._-]{3,30}$")) {
			System.out.println("NE MATCHVA parolata");
			return "password";
		}
		if (registerredUsers.containsKey(email)) {
			return "Email is already used";

		}

		System.out.println("REGISTERITE" + registerredUsers.size());
		System.out.println(this);

		return "register";

	}
	
	
	

	public String loginValidation(String email, String password) {

		if (registerredUsers.containsKey(email)) {
			System.out.println("CHECK THIS"+registerredUsers.get(email));
			System.out.println(MD5Convert(password));
			if (registerredUsers.get(email).equals(MD5Convert(password).toString())) {
				System.out.println("ERROR ?");
				loggingUser(email);
				return "success";
			} else {
				return "Wrong Password";
			}

		} else {
			return "Invalid email";
		}

	}

	private void loggingUser(String email) {

		loggedUsers.put(email, getUserbyEmail(email));
		System.out.println(loggedUsers.get(email).getFirstName());
		
	}
	public User addUserToSession(String email) {

		
		
		return getUserbyEmail(email);
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
