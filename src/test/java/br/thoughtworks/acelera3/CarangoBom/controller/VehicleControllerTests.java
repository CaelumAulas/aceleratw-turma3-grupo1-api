package br.thoughtworks.acelera3.CarangoBom.controller;

import java.net.URISyntaxException;
import java.util.Locale;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import br.thoughtworks.acelera3.CarangoBom.dto.VehicleListDto;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VehicleControllerTests extends ControllerTests {

  @BeforeAll
  private static void createVehicleUri() throws URISyntaxException {
    createUri("/vehicle");
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
    Assert.assertFalse(vehicles.getHasNext());
    Assert.assertFalse(vehicles.getHasPrevious());
  }
  
  @Test
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
  public void shouldCreateANewVehicle() throws Exception{
    String brandName = "Fiat";
    String model = "Toro";
    int year = 2020;
    double price = 60000;
    String body = String.format(Locale.US, "{ \"brandName\":\"%s\", \"model\":\"%s\", \"year\":%d, \"price\":%.2f }", brandName, model, year, price);
  
    postMockWithToken(body, 201, getToken("teste", "123"));
  }
  
  @Test
  public void shouldNotReturnVehicleByWrongId() throws Exception {
    Vehicle vehicle = mapFromJson(getMock("/9999"), Vehicle.class);
    Assert.assertNull(vehicle);
  }
  
  @Test 
  public void shouldDeleteAVehicleById() throws Exception{
    Vehicle vehicleBefore = mapFromJson(getMock("/4"), Vehicle.class);
    
    deleteMock(4l, 200, getToken("teste", "123"));
    
    Vehicle vehicleAfter = mapFromJson(getMock("/4"), Vehicle.class);
    
    Assert.assertEquals(new Long(4), vehicleBefore.getId());
    Assert.assertNull(vehicleAfter);
  }

  @Test 
  public void shouldNotDeleteAnNonExistingVehicleById() throws Exception{

    deleteMock(9999l, 404, getToken("teste", "123"));
  }
  
  @Test 
  public void shouldNotDeleteAVehicleWithoutAuthentication() throws Exception{

    deleteMock(1l, 403, getToken("teste", "1234"));
  }
  
  @Test 
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
  public void shouldNotEditAnExistingVehicleWithoutAuthentication() throws Exception {
	putMock(1l, "", 403, getToken("teste", "1234"));
  }
  
  @Test 
  public void shouldNotInsertAVehicleWithoutAuthentication() throws Exception{

    postMockWithToken("", 403, getToken("teste", "1234"));
  }
  
  @Test
  @Order(19)
  public void shouldReturnVehiclesWithoutPagination() throws Exception {
    Vehicle[] vehicles = mapFromJson(getMock("/all"), Vehicle[].class);
    Assert.assertEquals(4, vehicles.length);
  }
  
}

