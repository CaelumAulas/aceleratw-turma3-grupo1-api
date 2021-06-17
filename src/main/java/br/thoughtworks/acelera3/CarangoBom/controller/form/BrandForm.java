package br.thoughtworks.acelera3.CarangoBom.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.thoughtworks.acelera3.CarangoBom.models.Brand;

public class BrandForm {
	
	@NotNull
	@NotEmpty
	private String name;

	public String getName() {
		return name;
	}
	
	public Brand toBrand() {
		Brand brand = new Brand(this.name);
		return brand;
	}
}
