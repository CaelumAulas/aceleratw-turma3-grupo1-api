package br.thoughtworks.acelera3.CarangoBom.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VehicleRepositoryTests {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	private static Pageable pagination = PageRequest.of(0, 10);
	
	@Test
	@Order(1)
	public void shouldFindVehicleById() {
		Optional<Vehicle> vehicle = vehicleRepository.findById(1l);
		
		Assert.assertTrue(vehicle.isPresent());
	}
	
	@Test
	@Order(2)
	public void shouldNotFindVehicleByNonExistingId() {
		Optional<Vehicle> vehicle = vehicleRepository.findById(999l);
		
		Assert.assertFalse(vehicle.isPresent());
	}
	

	@Test
	@Order(3)
	public void shouldFindVehiclesByYear() {
		Page<Vehicle> vehicles_2020 = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",2020,2020,0,99999,pagination);
		Page<Vehicle> vehicles_2021 = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",2021,2021,0,99999,pagination);
		Page<Vehicle> vehicles_2022 = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",2022,2022,0,99999,pagination);
		Assert.assertEquals(1, vehicles_2020.getTotalElements());
		Assert.assertEquals(2, vehicles_2021.getTotalElements());
		Assert.assertEquals(0, vehicles_2022.getTotalElements());
	}
	
	@Test
	@Order(4)
	public void shouldFindVehiclesByBrandName() {		
		Page<Vehicle> vehicles = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("Fiat","",1900,3000,0,99999,pagination);
		Assert.assertEquals(1, vehicles.getTotalElements());
	}
	
	@Test
	@Order(5)
	public void shouldFindVehiclesByModel() {
		Page<Vehicle> vehicles = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","Ka",1900,3000,0,99999,pagination);
		Assert.assertEquals(1, vehicles.getTotalElements());
	}
	
	@Test
	@Order(6)
	public void shouldFindVehicleByPriceRange() {
		Page<Vehicle> vehicles = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",1900,3000,20000,30000,pagination);
		Assert.assertEquals(1, vehicles.getTotalElements());
		
		Page<Vehicle> vehicles_2 = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",1900,3000,10000,40000,pagination);
		Assert.assertEquals(3, vehicles_2.getTotalElements());
	}
	
//	@Test
//	@Order(7)
//	public void shouldCreateANewVehicle() {
//		Brand brand = new Brand("VW");
//		vehicleCrud = new Vehicle(brand, "Fusca", 1950, 3000.99);
//		vehicleRepository.save(vehicleCrud);
//		
//		Optional<Vehicle> findVehicle = vehicleRepository.findById(vehicleCrud.getId());
//		
//		Assert.assertTrue(findVehicle.isPresent());
//		Assert.assertEquals(vehicleCrud, findVehicle.get());
//	}
//	
//	@Test
//	@Order(8)
//	public void shouldDeleteVehicleById() {
//		Page<Vehicle> vehicles = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("", "Fusca", 1900, 3000, 0, 10000, pagination);
//		Vehicle vehicle = vehicles.getContent().get(0);
//		long id = vehicle.getId();
//		
//		vehicleRepository.deleteById(id);
//		
//		Optional<Vehicle> deletedVehicle = vehicleRepository.findById(id);
//		
//		Assert.assertFalse(deletedVehicle.isPresent());
//	}
}
