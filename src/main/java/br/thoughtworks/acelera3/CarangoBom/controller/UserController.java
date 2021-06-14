package br.thoughtworks.acelera3.CarangoBom.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.thoughtworks.acelera3.CarangoBom.controller.form.UserForm;
import br.thoughtworks.acelera3.CarangoBom.dto.UserDto;
import br.thoughtworks.acelera3.CarangoBom.dto.UserListDto;
import br.thoughtworks.acelera3.CarangoBom.models.User;
import br.thoughtworks.acelera3.CarangoBom.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping
	public ResponseEntity<UserListDto> getAllUsers(@PageableDefault(sort="id", direction=Direction.ASC) Pageable pagination) {

		Page<User> users = userRepository.findAll(pagination);

		return ResponseEntity.ok(new UserListDto(users));
	}
	
	
	@GetMapping("/{username}")
	public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
		Optional<User> user = userRepository.findByUsername(username);
		if (user.isPresent()){
			return ResponseEntity.ok(new UserDto(user.get()));
		}
		
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {
		User user = form.toUser();
		
		try {
			userRepository.save(user);
			URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
			return ResponseEntity.created(uri).body(new UserDto(user));
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().body("Usuário já existente.");
		}
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable String username) {
		Optional<User> user = userRepository.findByUsername(username);
		
		if (user.isPresent()) {
			userRepository.delete(user.get());
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{username}")
	public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserForm form) {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
			userRepository.save(user);
			return ResponseEntity.ok(new UserDto(user));
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
