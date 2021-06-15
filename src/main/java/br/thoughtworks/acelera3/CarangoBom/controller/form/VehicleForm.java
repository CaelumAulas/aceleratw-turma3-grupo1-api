package br.thoughtworks.acelera3.CarangoBom.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.thoughtworks.acelera3.CarangoBom.models.Brand;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;
import br.thoughtworks.acelera3.CarangoBom.repository.BrandRepository;

public class VehicleForm {

  @NotNull
  @NotEmpty
  private String brandName;
  
  @NotNull
  @NotEmpty
  private String model;
  
  @NotNull
  private int year;
  
  @NotNull
  private double price;
  
  
  public String getBrandName() {
    return brandName;
  }
  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }
  public String getModel() {
    return model;
  }
  public void setModel(String model) {
    this.model = model;
  }
  public int getYear() {
    return year;
  }
  public void setYear(int year) {
    this.year = year;
  }
  public double getPrice() {
    return price;
  }
  public void setPrice(double price) {
    this.price = price;
  }
  public Vehicle toVehicle(BrandRepository brandRepository) {
    Optional<Brand> optionalBrand = brandRepository.findByName(brandName);
    return new Vehicle(optionalBrand.orElse(new Brand(brandName)), model, year, price);
  }
  
}

