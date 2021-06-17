package br.thoughtworks.acelera3.CarangoBom.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.thoughtworks.acelera3.CarangoBom.models.User;

public class UserForm {
	
	@NotNull
	@NotEmpty
	@Length(min=6)
	private String username;
	
	@NotNull
	@NotEmpty
	@Length(min=6)
	private String password;
	
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
	
	public User toUser() {
		String encryptedPassword = new BCryptPasswordEncoder().encode(password);
		return new User(username, encryptedPassword);
	}
}
