package br.thoughtworks.acelera3.CarangoBom.dto;

import org.springframework.data.domain.Page;

import br.thoughtworks.acelera3.CarangoBom.models.User;

public class UserDto {
	
	private Long id;
	private String username;
	
	public UserDto() { }
	
	public UserDto(User input) {
		this.id = input.getId();
		this.username = input.getUsername();
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public static Page<UserDto> page(Page<User> input){
		return input.map(UserDto::new);
	}
}
