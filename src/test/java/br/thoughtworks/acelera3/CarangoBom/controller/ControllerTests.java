package br.thoughtworks.acelera3.CarangoBom.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ControllerTests {
  
	@Autowired
	protected MockMvc mockMvc;
  
	protected static URI uri;

	protected static void createUri(String uriParam) throws URISyntaxException {
		uri = new URI(String.format("/%s", uriParam));
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
}
