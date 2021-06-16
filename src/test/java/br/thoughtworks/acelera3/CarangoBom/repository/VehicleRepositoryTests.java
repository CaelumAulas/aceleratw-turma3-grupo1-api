package br.thoughtworks.acelera3.CarangoBom.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class VehicleRepositoryTests {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	private static Pageable pagination = PageRequest.of(0, 10);
	
	@Test
	public void shouldFindVehicleById() {
		Optional<Vehicle> vehicle = vehicleRepository.findById(1l);
		
		Assert.assertTrue(vehicle.isPresent());
	}
	
	@Test
	public void shouldNotFindVehicleByNonExistingId() {
		Optional<Vehicle> vehicle = vehicleRepository.findById(999l);
		
		Assert.assertFalse(vehicle.isPresent());
	}
	

	@Test
	public void shouldFindVehiclesByYear() {
		Page<Vehicle> vehicles_2020 = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",2020,2020,0,99999,pagination);
		Page<Vehicle> vehicles_2021 = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",2021,2021,0,99999,pagination);
		Page<Vehicle> vehicles_2022 = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",2022,2022,0,99999,pagination);
		Assert.assertEquals(1, vehicles_2020.getTotalElements());
		Assert.assertEquals(2, vehicles_2021.getTotalElements());
		Assert.assertEquals(0, vehicles_2022.getTotalElements());
	}
	
	@Test
	public void shouldFindVehiclesByBrandName() {		
		Page<Vehicle> vehicles = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("Fiat","",1900,3000,0,99999,pagination);
		Assert.assertEquals(1, vehicles.getTotalElements());
	}
	
	@Test
	public void shouldFindVehiclesByModel() {
		Page<Vehicle> vehicles = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","Ka",1900,3000,0,99999,pagination);
		Assert.assertEquals(1, vehicles.getTotalElements());
	}
	
	@Test
	public void shouldFindVehicleByPriceRange() {
		Page<Vehicle> vehicles = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",1900,3000,20000,30000,pagination);
		Assert.assertEquals(1, vehicles.getTotalElements());
		
		Page<Vehicle> vehicles_2 = vehicleRepository.findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween("","",1900,3000,10000,40000,pagination);
		Assert.assertEquals(3, vehicles_2.getTotalElements());
	}
	
}
