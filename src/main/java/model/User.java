package model;

public class User {
	private int user_id;
	private String password;
	private String email;
	private char gender;
	private String name;
	private String profile_photo_url;
	private int role_id;

	public User(int user_id, String password, String email, char gender, String name, String profile_photo_url, int role_id) {
		super();
		this.user_id = user_id;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.name = name;
		this.profile_photo_url = profile_photo_url;
		this.role_id = role_id;
	}
	
	//Default constructor
	public User() {
		
	}
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public char getGender() {
		return gender;
	}
	
	public void setGender(char gender) {
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProfile_photo_url() {
		return profile_photo_url;
	}
	
	public void setProfile_photo_url(String profile_photo_url) {
		this.profile_photo_url = profile_photo_url;
	}
	
	public int getRole_id() {
		return role_id;
	}
	
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
}
