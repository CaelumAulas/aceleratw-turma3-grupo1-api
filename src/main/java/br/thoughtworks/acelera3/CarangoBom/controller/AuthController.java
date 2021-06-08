package br.thoughtworks.acelera3.CarangoBom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.thoughtworks.acelera3.CarangoBom.config.security.TokenService;
import br.thoughtworks.acelera3.CarangoBom.dto.LoginDto;
import br.thoughtworks.acelera3.CarangoBom.dto.TokenDto;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<TokenDto> authenticate(@RequestBody LoginDto login) {
		
		
		UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
		
		try {
			Authentication authentication = authenticationManager.authenticate(loginData);
			String token = tokenService.generateToken(authentication);
			return ResponseEntity.ok(new TokenDto("Bearer", token));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
