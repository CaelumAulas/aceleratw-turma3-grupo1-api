package br.thoughtworks.acelera3.CarangoBom.dto;

import br.thoughtworks.acelera3.CarangoBom.models.Brand;

public class BrandDto {

	private Long id;
	private String name;
	
	public BrandDto() { }
	
	public BrandDto(Brand input) {
		this.id = input.getId();
		this.name = input.getName();
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
