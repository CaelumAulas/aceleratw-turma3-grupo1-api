package br.thoughtworks.acelera3.CarangoBom.controller;

import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests extends ControllerTests {
	
	@BeforeAll
	private static void createUri() throws URISyntaxException {
		createUri("/auth");
	}

	private String getBody(String username, String password) {
		return String.format("{ \"username\":\"%s\", \"password\":\"%s\"}", username, password);
	}
	
	@Test
	public void shouldAuthenticateUser() throws Exception {	
		String body = getBody("teste", "123");
		
		postMock(body, 200);
	}
	
	@Test
	public void shouldNotAuthenticateUser() throws Exception {
		String body = getBody("teste", "1234");
		
		postMock(body, 400);
	} 

}
