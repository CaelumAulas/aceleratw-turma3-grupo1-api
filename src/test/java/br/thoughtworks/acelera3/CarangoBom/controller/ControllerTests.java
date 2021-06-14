package br.thoughtworks.acelera3.CarangoBom.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.thoughtworks.acelera3.CarangoBom.dto.TokenDto;

public abstract class ControllerTests {
  
	@Autowired
	protected MockMvc mockMvc;
  
	protected static URI uri;

	protected static void createUri(String uriParam) throws URISyntaxException {
		uri = new URI(uriParam);
	}

	protected TokenDto getToken(String username, String password) throws UnsupportedEncodingException, URISyntaxException, Exception {
		String content = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);
		String token = mockMvc.perform(MockMvcRequestBuilders.post(new URI("/auth"))
				.content(content)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse()
				.getContentAsString();
		
		if(token.isEmpty()) {
			return new TokenDto("", "");
		}
		
		return mapFromJson(token, TokenDto.class);
	}

	protected String getMock(String path) throws UnsupportedEncodingException, Exception {
		return mockMvc.perform(MockMvcRequestBuilders.get(uri + path)).andReturn().getResponse().getContentAsString();
    }
	
	protected <T> T mapFromJson(String json, Class<T> classType) throws JsonMappingException, JsonProcessingException {
	    if (json == null || json.trim().isEmpty()) {
	      return null;
	    }
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.readValue(json, classType);
	}
		  
	protected void postMockWithToken(String body, int statusCode, TokenDto token) throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.post(uri)
	        .content(body)
	        .header("Authorization", String.format("%s %s", token.getType(), token.getToken()))
	        .contentType(MediaType.APPLICATION_JSON))
	    .andExpect(MockMvcResultMatchers.status().is(statusCode));
	}
	
	protected void postMock(String body, int statusCode) throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.content(body)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(statusCode));
	}
	
	  
	protected void deleteMock(long id, int statusCode, TokenDto token) throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.delete(uri + "/" + id)
	    		.header("Authorization", String.format("%s %s", token.getType(), token.getToken())))
	    .andExpect(MockMvcResultMatchers.status().is(statusCode));
	}
	
	protected void putMock(long id, String body, int statusCode, TokenDto token) throws Exception {
	    mockMvc.perform(MockMvcRequestBuilders.put(uri + "/" + id)
	    		.content(body)
	    		.header("Authorization", String.format("%s %s", token.getType(), token.getToken()))
	        .contentType(MediaType.APPLICATION_JSON))
	    	.andExpect(MockMvcResultMatchers.status().is(statusCode));
	  	}
	}
