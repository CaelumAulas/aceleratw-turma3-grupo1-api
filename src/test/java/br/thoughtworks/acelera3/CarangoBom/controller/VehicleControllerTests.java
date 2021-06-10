package br.thoughtworks.acelera3.CarangoBom.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.thoughtworks.acelera3.CarangoBom.dto.VehicleListDto;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
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

	private <T> T mapFromJson(String json, Class<T> classType) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, classType);
	}

	@Test
	public void shouldReturnVehicleById() throws Exception {
		Vehicle vehicle = mapFromJson(getMock("/1"), Vehicle.class);
		Assert.assertEquals(new Long(1), vehicle.getId());
		Assert.assertEquals("Ford", vehicle.getBrand().getName());
		Assert.assertEquals("Ka", vehicle.getModel());
		Assert.assertEquals(2021, vehicle.getYear());
		Assert.assertEquals(24000.99, vehicle.getPrice(), 0.0);
	}
			
	@Test
	public void shouldReturnVehicles() throws Exception {
		VehicleListDto vehicles = mapFromJson(getMock("?page=0&size=20"), VehicleListDto.class);
		Assert.assertEquals(4, vehicles.getContent().size());
		Assert.assertEquals(0, vehicles.getPage());
		Assert.assertEquals(1, vehicles.getTotalPages());
		Assert.assertEquals(20, vehicles.getPageSize());
		Assert.assertFalse(vehicles.hasNext());
		Assert.assertFalse(vehicles.hasPrevious());
	}
	
	@Test
	public void shouldReturnVehiclesByBrand() throws Exception {
		VehicleListDto vehicles = mapFromJson(getMock("?brand=Ford&page=0&size=10"), VehicleListDto.class);
		Assert.assertEquals(3, vehicles.getContent().size());
		Assert.assertEquals(0, vehicles.getPage());
		Assert.assertEquals(1, vehicles.getTotalPages());
		Assert.assertEquals(10, vehicles.getPageSize());
		Assert.assertFalse(vehicles.hasNext());
		Assert.assertFalse(vehicles.hasPrevious());
	}

}
