package br.thoughtworks.acelera3.CarangoBom.dto;

import org.springframework.data.domain.Page;

import br.thoughtworks.acelera3.CarangoBom.models.Brand;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;

public class VehicleDto {
	
	private Long id;
	private Brand brand;
	private String model;
	private int year;
	private double price;

	public VehicleDto() { }
	
	public VehicleDto(Vehicle input) {
		this.id = input.getId();
		this.brand = input.getBrand();
		this.model = input.getModel();
		this.year = input.getYear();
		this.price = input.getPrice();
	}

	public Long getId() {
		return id;
	}

	public Brand getBrand() {
		return brand;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}

	public double getPrice() {
		return price;
	}
	
	public static Page<VehicleDto> page(Page<Vehicle> input){
		return input.map(VehicleDto::new);
	}
}
