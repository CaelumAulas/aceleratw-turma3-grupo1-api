package br.thoughtworks.acelera3.CarangoBom.repository;

import java.util.List;
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

import br.thoughtworks.acelera3.CarangoBom.models.Brand;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class VehicleRepositoryTests {

	@Autowired
	private VehicleRepository vehicleRepository;
	
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
		List<Vehicle> vehicles_2020 = vehicleRepository.findAllByYear(2020);
		List<Vehicle> vehicles_2021 = vehicleRepository.findAllByYear(2021);
		List<Vehicle> vehicles_2022 = vehicleRepository.findAllByYear(2022);
		Assert.assertEquals(1, vehicles_2020.size());
		Assert.assertEquals(2, vehicles_2021.size());
		Assert.assertEquals(0, vehicles_2022.size());
	}
	
	@Test
	public void shouldFindVehiclesByBrand() {
		Brand brand = new Brand("Ford");
		brand.setId(1l);
		List<Vehicle> ford_vehicles = vehicleRepository.findAllByBrand(brand);
		Assert.assertEquals(3, ford_vehicles.size());
	}
	
	@Test
	public void shouldFindVehiclesByBrandName() {
		Pageable pagination = PageRequest.of(0, 10);
		Page<Vehicle> vehicles = vehicleRepository.findAllByBrandName("Fiat", pagination);
		Assert.assertEquals(1, vehicles.getTotalElements());
	}
	
	@Test
	public void shouldFindVehiclesByModel() {
		List<Vehicle> vehicles = vehicleRepository.findAllByModel("Ka");
		Assert.assertEquals(1, vehicles.size());
	}
	
	@Test
	public void shouldFindVehicleByPriceRange() {
		List<Vehicle> vehicles = vehicleRepository.findByPriceBetween(20000.00, 30000.00);
		Assert.assertEquals(1, vehicles.size());
		
		List<Vehicle> vehicles_2 = vehicleRepository.findByPriceBetween(10000.00, 40000.00);
		Assert.assertEquals(3, vehicles_2.size());
	}
}
