package br.thoughtworks.acelera3.CarangoBom.controller;

import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import br.thoughtworks.acelera3.CarangoBom.dto.TokenDto;
import br.thoughtworks.acelera3.CarangoBom.dto.UserListDto;
import br.thoughtworks.acelera3.CarangoBom.models.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTests extends ControllerTests {

	@BeforeAll
	private static void createUserUri() throws URISyntaxException {
		createUri("/user");
	}
	
	private void deleteMock(String username, int statusCode, TokenDto token) throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.delete(uri + "/" + username)
	    		.header("Authorization", String.format("%s %s", token.getType(), token.getToken())))
	    .andExpect(MockMvcResultMatchers.status().is(statusCode));
	}
	
	private void putMock(String username, String body, int statusCode, TokenDto token) throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(uri + "/" + username)
				.content(body)
				.header("Authorization", String.format("%s %s", token.getType(), token.getToken()))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(statusCode));
	}

	@Test
	public void shouldNotFindUserListWhenNotAuthenticated() throws Exception {
		UserListDto user = mapFromJson(getMock("?page=0&size=20"), UserListDto.class);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldNotFindUserListWhenAuthCredentialsAreWrong() throws Exception {
		UserListDto user = mapFromJson(getMockWithToken("?page=0&size=20", getToken("teste", "senhaerrada")), UserListDto.class);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldFindUserList() throws Exception {
		UserListDto user = mapFromJson(getMockWithToken("?page=0&size=20", getToken("teste", "123")), UserListDto.class);
		Assert.assertEquals(2, user.getContent().size());
		Assert.assertEquals(0, user.getPage());
		Assert.assertEquals(1, user.getTotalPages());
		Assert.assertEquals(20, user.getPageSize());
		Assert.assertFalse(user.getHasNext());
		Assert.assertFalse(user.getHasPrevious());
	}
	
	@Test
	public void shouldFindUserByUsername() throws Exception {
		User user =  mapFromJson(getMockWithToken("/teste", getToken("teste", "123")), User.class);
		Assert.assertEquals("teste", user.getUsername());
		Assert.assertEquals(1l, user.getId(), 0.0);
	}
	
	@Test
	public void shouldNotFindUserByNonExistingUsername() throws Exception {
		User user =  mapFromJson(getMockWithToken("/usuario", getToken("teste", "123")), User.class);
		Assert.assertNull(user);
	}
	
	@Test
	public void shouldNotFindUserByUsernameWhenNotAuthenticated() throws Exception {
		User user =  mapFromJson(getMock("/teste"), User.class);
		Assert.assertNull(user);
	}

	@Test
	public void shouldNotFindUserByUsernameWhenAuthCredentialsAreWrong() throws Exception {
		User user =  mapFromJson(getMockWithToken("/teste", getToken("teste", "senhaerrada")), User.class);
		Assert.assertNull(user);
	}

	@Test
	public void shouldCreateANewUser() throws Exception {
		String username = "newUser";
		String password = "pass123";
		String body = String.format("{ \"username\":\"%s\", \"password\":\"%s\" }", username, password);

		postMock(body, HttpStatus.CREATED.value());
	}

	@Test
	public void shouldNotCreateANewUserWithoutPassword() throws Exception {
		String username = "newUser";
		String password = "";
		String body = String.format("{ \"username\":\"%s\", \"password\":\"%s\" }", username, password);

		postMock(body, HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void shouldNotCreateANewUserWithoutUsernameAndWithoutPassword() throws Exception {
		String username = "";
		String password = "";
		String body = String.format("{ \"username\":\"%s\", \"password\":\"%s\" }", username, password);

		postMock(body, HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void shouldNotCreateANewUserWithExistingUsername() throws Exception {
		String username = "newUser";
		String password = "pass123";
		String body = String.format("{ \"username\":\"%s\", \"password\":\"%s\" }", username, password);

		postMock(body, HttpStatus.CREATED.value());

		String username2 = "newUser";
		String password2 = "pass1234";
		String body2 = String.format("{ \"username\":\"%s\", \"password\":\"%s\" }", username2, password2);

		postMock(body2, HttpStatus.BAD_REQUEST.value());
	}

	@Test 
	public void shouldDeleteAUserByUsername() throws Exception{
		User userBefore = mapFromJson(getMockWithToken("/teste", getToken("teste", "123")), User.class);

		deleteMock(userBefore.getUsername(), 200, getToken("teste", "123"));

		User userAfter = mapFromJson(getMockWithToken("/teste", getToken("teste2", "123")), User.class);

		Assert.assertNull(userAfter);
	}
	
	@Test 
	public void shouldNotDeleteAUserByUsernameWhenNotAuthenticated() throws Exception{
		deleteMock("teste", 403, getToken("teste", "1234"));
	}
	
	@Test
	public void shouldUpdateAnExistingUser() throws Exception {
		User user = mapFromJson(getMockWithToken("/teste2", getToken("teste", "123")), User.class);
	    Assert.assertNotEquals("", getToken(user.getUsername(), "123").getToken());
		
	    String body = String.format("{ \"username\":\"%s\", \"password\":\"%s\" }", 
	        user.getUsername(), "1234");
	    
	    putMock(user.getUsername(), body, 200, getToken("teste", "123"));
	    
	    
	    Assert.assertNotEquals("", getToken(user.getUsername(), "1234").getToken());
	}
	

}
