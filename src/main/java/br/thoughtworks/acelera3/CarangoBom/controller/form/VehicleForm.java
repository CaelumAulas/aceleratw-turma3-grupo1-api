package br.thoughtworks.acelera3.CarangoBom.controller.form;

import java.util.Optional;

import br.thoughtworks.acelera3.CarangoBom.models.Brand;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;
import br.thoughtworks.acelera3.CarangoBom.repository.BrandRepository;

public class VehicleForm {

  private String brandName;
  private String model;
  private int year;
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

