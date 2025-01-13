package model;

public class Role {
	private int role_id;
	private String name;
	private String description;
	
	public Role(int role_id, String name, String description) {
		this.role_id = role_id;
		this.name = name;
		this.description = description;
	}
	
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
