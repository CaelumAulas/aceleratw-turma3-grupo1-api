package br.thoughtworks.acelera3.CarangoBom.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	private static URI uri;
	
	@BeforeAll
	private static void createUri() throws URISyntaxException {
		uri = new URI("/auth");
	}
	
	@Test
	public void shouldAuthenticateUser() throws Exception {	
		String body = "{ \"username\":\"teste\", \"password\":\"123\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri)
					.content(body)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	public void shouldNotAuthenticateUser() throws Exception {
		String body = "{ \"username\":\"teste\", \"password\":\"1234\"}";
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri)
					.content(body)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is(400));
	} 

}
