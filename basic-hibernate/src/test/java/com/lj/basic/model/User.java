package com.lj.basic.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 这是测试类，该测试类只是用来测试基于hibernate的dao基类。 不会被打包。
 * @author Administrator
 *
 */
@Entity
@Table(name = "basic_t_user")
public class User {
	
	//这里只是测试basedao，所以参数用少一些
	private int id;
	private String username;
	private String password;
	
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public User(int id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public User() {
		super();
	}
	
	
	

	

}
