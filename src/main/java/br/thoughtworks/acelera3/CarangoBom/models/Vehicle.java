package br.thoughtworks.acelera3.CarangoBom.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@NotEmpty
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Brand brand;
	
	@NotNull
	@NotEmpty
	private String model;
	
	@NotNull
	@NotEmpty
	private int year;
	
	@NotNull
	@NotEmpty
	private double price;
	
	public Vehicle() { }
	
	public Vehicle(Brand brand, String model, int year, double price) {
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.price = price;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	
	
}
