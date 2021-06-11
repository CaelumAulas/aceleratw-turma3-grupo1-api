package br.thoughtworks.acelera3.CarangoBom.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.thoughtworks.acelera3.CarangoBom.dto.TokenDto;
import br.thoughtworks.acelera3.CarangoBom.dto.VehicleListDto;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class VehicleControllerTests {
  
  @Autowired
  private MockMvc mockMvc;
  
  private static URI uri;
  
  @BeforeAll
  private static void createUri() throws URISyntaxException {
    uri = new URI("/vehicle");
  }
  
  private String getMock(String path) throws UnsupportedEncodingException, Exception {
    return mockMvc.perform(MockMvcRequestBuilders.get(uri + path)).andReturn().getResponse().getContentAsString();
  }
  
  private TokenDto getToken(String username, String password) throws UnsupportedEncodingException, URISyntaxException, Exception {
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
  
  private void postMock(String body, int statusCode, TokenDto token) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post(uri)
        .content(body)
        .header("Authorization", String.format("%s %s", token.getType(), token.getToken()))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(MockMvcResultMatchers.status().is(statusCode));
  }
  
  private void deleteMock(long id, int statusCode, TokenDto token) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete(uri + "/" + id)
    	.header("Authorization", String.format("%s %s", token.getType(), token.getToken())))
    .andExpect(MockMvcResultMatchers.status().is(statusCode));
  }

  private void putMock(long id, String body, int statusCode, TokenDto token) throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put(uri + "/" + id)
        .content(body)
        .header("Authorization", String.format("%s %s", token.getType(), token.getToken()))
        .contentType(MediaType.APPLICATION_JSON))
    .andExpect(MockMvcResultMatchers.status().is(statusCode));
  }
  
  private <T> T mapFromJson(String json, Class<T> classType) throws JsonMappingException, JsonProcessingException {
    if (json == null || json.trim().isEmpty()) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, classType);
  }

  @Test
  @Order(1)
  public void shouldReturnVehicleById() throws Exception {
    Vehicle vehicle = mapFromJson(getMock("/1"), Vehicle.class);
    Assert.assertEquals(new Long(1), vehicle.getId());
    Assert.assertEquals("Ford", vehicle.getBrand().getName());
    Assert.assertEquals("Ka", vehicle.getModel());
    Assert.assertEquals(2021, vehicle.getYear());
    Assert.assertEquals(24000.99, vehicle.getPrice(), 0.0);
  }
      
  @Test
  @Order(2)
  public void shouldReturnVehicles() throws Exception {
    VehicleListDto vehicles = mapFromJson(getMock("?page=0&size=20"), VehicleListDto.class);
    Assert.assertEquals(4, vehicles.getContent().size());
    Assert.assertEquals(0, vehicles.getPage());
    Assert.assertEquals(1, vehicles.getTotalPages());
    Assert.assertEquals(20, vehicles.getPageSize());
    Assert.assertFalse(vehicles.getHasNext());
    Assert.assertFalse(vehicles.getHasPrevious());
  }
  
  @Test
  @Order(3)
  public void shouldReturnVehiclesByBrand() throws Exception {
    VehicleListDto vehicles = mapFromJson(getMock("?brand=Ford&page=0&size=10"), VehicleListDto.class);
    Assert.assertEquals(3, vehicles.getContent().size());
    Assert.assertEquals(0, vehicles.getPage());
    Assert.assertEquals(1, vehicles.getTotalPages());
    Assert.assertEquals(10, vehicles.getPageSize());
    Assert.assertFalse(vehicles.getHasNext());
    Assert.assertFalse(vehicles.getHasPrevious());
  }
  
  @Test
  @Order(4)
  public void shouldReturnVehiclesByModel() throws Exception {
    VehicleListDto vehicles = mapFromJson(getMock("?model=Uno&page=0&size=10"), VehicleListDto.class);
    Assert.assertEquals(1, vehicles.getContent().size());
    Assert.assertEquals(0, vehicles.getPage());
    Assert.assertEquals(1, vehicles.getTotalPages());
    Assert.assertEquals(10, vehicles.getPageSize());
    Assert.assertFalse(vehicles.getHasNext());
    Assert.assertFalse(vehicles.getHasPrevious());
  }
  
  @Test
  @Order(5)
  public void shouldReturnVehiclesByYearRange() throws Exception {
    VehicleListDto vehicles = mapFromJson(getMock("?minYear=2019&maxYear=2020&page=0&size=10"), VehicleListDto.class);
    Assert.assertEquals(1, vehicles.getContent().size());
    Assert.assertEquals(0, vehicles.getPage());
    Assert.assertEquals(1, vehicles.getTotalPages());
    Assert.assertEquals(10, vehicles.getPageSize());
    Assert.assertFalse(vehicles.getHasNext());
    Assert.assertFalse(vehicles.getHasPrevious());
  }
  
  @Test
  @Order(6)
  public void shouldReturnVehiclesByPriceRange() throws Exception {
    VehicleListDto vehicles = mapFromJson(getMock("?minPrice=20000.99&maxPrice=31000.99&page=0&size=10"), VehicleListDto.class);
    Assert.assertEquals(2, vehicles.getContent().size());
    Assert.assertEquals(0, vehicles.getPage());
    Assert.assertEquals(1, vehicles.getTotalPages());
    Assert.assertEquals(10, vehicles.getPageSize());
    Assert.assertFalse(vehicles.getHasNext());
    Assert.assertFalse(vehicles.getHasPrevious());
  }

  @Test
  @Order(7)
  public void shouldReturnAllVehiclesWithPaginationAndIsFirstPage() throws Exception {
    VehicleListDto vehicles = mapFromJson(getMock("?page=0&size=2"), VehicleListDto.class);
    Assert.assertEquals(2, vehicles.getContent().size());
    Assert.assertEquals(0, vehicles.getPage());
    Assert.assertEquals(2, vehicles.getTotalPages());
    Assert.assertEquals(2, vehicles.getPageSize());
    Assert.assertTrue(vehicles.getHasNext());
    Assert.assertFalse(vehicles.getHasPrevious());
  }
  
  @Test
  @Order(8)
  public void shouldReturnAllVehiclesWithPaginationAndIsSecondPage() throws Exception {
    VehicleListDto vehicles = mapFromJson(getMock("?page=1&size=2"), VehicleListDto.class);
    Assert.assertEquals(2, vehicles.getContent().size());
    Assert.assertEquals(1, vehicles.getPage());
    Assert.assertEquals(2, vehicles.getTotalPages());
    Assert.assertEquals(2, vehicles.getPageSize());
    Assert.assertFalse(vehicles.getHasNext());
    Assert.assertTrue(vehicles.getHasPrevious());
  }
  
  @Test
  @Order(9)
  public void shouldReturnVehiclesByModelAndBrand() throws Exception {
    VehicleListDto vehicles = mapFromJson(getMock("?model=Fusion&brand=Ford&page=0&size=10"), VehicleListDto.class);
    Assert.assertEquals(1, vehicles.getContent().size());
    Assert.assertEquals(0, vehicles.getPage());
    Assert.assertEquals(1, vehicles.getTotalPages());
    Assert.assertEquals(10, vehicles.getPageSize());
    Assert.assertFalse(vehicles.getHasNext());
    Assert.assertFalse(vehicles.getHasPrevious());
  }
  
  @Test
  @Order(10)
  public void shouldNotReturnVehiclesByWrongModelAndBrand() throws Exception {
    VehicleListDto vehicles = mapFromJson(getMock("?model=Fusion&brand=Fiat&page=0&size=10"), VehicleListDto.class);
    Assert.assertEquals(0, vehicles.getContent().size());
    Assert.assertEquals(0, vehicles.getPage());
    Assert.assertEquals(0, vehicles.getTotalPages());
    Assert.assertEquals(10, vehicles.getPageSize());
    Assert.assertFalse(vehicles.getHasNext());
    Assert.assertFalse(vehicles.getHasPrevious());
  }
  
  @Test 
  @Order(11)
  public void shouldCreateANewVehicle() throws Exception{
    String brandName = "Fiat";
    String model = "Toro";
    int year = 2020;
    double price = 60000;
    String body = String.format(Locale.US, "{ \"brandName\":\"%s\", \"model\":\"%s\", \"year\":%d, \"price\":%.2f }", brandName, model, year, price);
  
    postMock(body, 201, getToken("teste", "123"));
  }
  
  @Test
  @Order(12) 
  public void shouldNotReturnVehicleByWrongId() throws Exception {
    Vehicle vehicle = mapFromJson(getMock("/9999"), Vehicle.class);
    Assert.assertNull(vehicle);
  }
  
  @Test 
  @Order(13)
  public void shouldDeleteAVehicleById() throws Exception{
    Vehicle vehicleBefore = mapFromJson(getMock("/5"), Vehicle.class);
    
    deleteMock(5l, 200, getToken("teste", "123"));
    
    Vehicle vehicleAfter = mapFromJson(getMock("/5"), Vehicle.class);
    
    Assert.assertEquals(new Long(5), vehicleBefore.getId());
    Assert.assertNull(vehicleAfter);
  }

  @Test 
  @Order(14)
  public void shouldNotDeleteAnNonExistingVehicleById() throws Exception{

    deleteMock(9999l, 404, getToken("teste", "123"));
  }
  
  @Test 
  @Order(15)
  public void shouldNotDeleteAVehicleWithoutAuthentication() throws Exception{

    deleteMock(1l, 403, getToken("teste", "1234"));
  }
  
  @Test 
  @Order(16)
  public void shouldEditAnExistingVehicle() throws Exception{
    Vehicle vehicleBefore = mapFromJson(getMock("/1"), Vehicle.class);
    Assert.assertNotEquals(60000.00, vehicleBefore.getPrice());
    
    String body = String.format(Locale.US, "{ \"brandName\":\"%s\", \"model\":\"%s\", \"year\":%d, \"price\":%.2f }", 
        vehicleBefore.getBrand().getName(), vehicleBefore.getModel(), vehicleBefore.getYear(), 60000.00);
    
    putMock(1l, body, 200, getToken("teste", "123"));
    
    Vehicle vehicle = mapFromJson(getMock("/1"), Vehicle.class);
    Assert.assertEquals(60000, vehicle.getPrice(), 0.0);
  }
  
  @Test
  @Order(17)
  public void shouldNotEditAnExistingVehicleWithoutAuthentication() throws Exception {
	putMock(1l, "", 403, getToken("teste", "1234"));
  }
  
  @Test 
  @Order(18)
  public void shouldNotInsertAVehicleWithoutAuthentication() throws Exception{

    postMock("", 403, getToken("teste", "1234"));
  }
  
  @Test
  @Order(19)
  public void shouldReturnVehiclesWithoutPagination() throws Exception {
    Vehicle[] vehicles = mapFromJson(getMock("/all"), Vehicle[].class);
    Assert.assertEquals(4, vehicles.length);
  }
  
}

