package br.thoughtworks.acelera3.CarangoBom.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.thoughtworks.acelera3.CarangoBom.controller.form.VehicleForm;
import br.thoughtworks.acelera3.CarangoBom.dto.VehicleDto;
import br.thoughtworks.acelera3.CarangoBom.dto.VehicleListDto;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;
import br.thoughtworks.acelera3.CarangoBom.repository.BrandRepository;
import br.thoughtworks.acelera3.CarangoBom.repository.VehicleRepository;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

  @Autowired
  private VehicleRepository vehicleRepository;
  
  @Autowired 
  private BrandRepository brandRepository;
  
  @GetMapping("/{id}")
  public ResponseEntity<VehicleDto> getById(@PathVariable Long id){
    
    Optional<Vehicle> vehicle = vehicleRepository.findById(id);
    
    if(vehicle.isPresent()) {
      return ResponseEntity.ok().body(new VehicleDto(vehicle.get()));
    }
    
    return ResponseEntity.notFound().build();
  }
  
  
  @GetMapping
  public ResponseEntity<VehicleListDto> getByParameters(@PageableDefault(sort="id", direction=Direction.ASC) Pageable pagination,
                              @RequestParam(required = false) String brand,
                              @RequestParam(required = false) String model,
                              @RequestParam(required = false) String minYear,
                              @RequestParam(required = false) String maxYear,
                              @RequestParam(required = false) String minPrice,
                              @RequestParam(required = false) String maxPrice)
  {

    double minPriceValue=0;
    double maxPriceValue=Double.MAX_VALUE;
    int minYearValue=1900;
    int maxYearValue=Integer.MAX_VALUE;

    if (brand == null) {
      brand="";
    }

    if (model == null) {
      model="";
    }

    if (minYear != null) {
      minYearValue = Integer.parseInt(minYear);
    }
    if (maxYear != null) {
      maxYearValue = Integer.parseInt(maxYear);
    }
    
    if (minPrice != null) {
      minPriceValue = Double.parseDouble(minPrice);
    }
    if (maxPrice != null) {
      maxPriceValue = Double.parseDouble(maxPrice);
    }
    
    Page<Vehicle> vehicles = vehicleRepository
      .findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween(brand, 
                                            model, 
                                            minYearValue,
                                            maxYearValue,
                                            minPriceValue,
                                            maxPriceValue,
                                            pagination);


    return ResponseEntity.ok(new VehicleListDto(vehicles));
    
    
  }
  
  @GetMapping("/all")
  public List<Vehicle> getAllVehiclesWithoutPagination() {
	  
	  return vehicleRepository.findAll();
  }
  
  @PostMapping
  public ResponseEntity<VehicleDto> createVehicle(@RequestBody @Valid VehicleForm form, UriComponentsBuilder uriBuilder) {
    Vehicle vehicle = form.toVehicle(brandRepository);
    vehicleRepository.save(vehicle);
    
    URI uri = uriBuilder.path("/vehicle/{id}").buildAndExpand(vehicle.getId()).toUri();
    return ResponseEntity.created(uri).body(new VehicleDto(vehicle));
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
    
    Optional<Vehicle> vehicle = vehicleRepository.findById(id);
    if (vehicle.isPresent()) {
    	vehicleRepository.deleteById(id);
    	return ResponseEntity.ok().build();
    }
    
    return ResponseEntity.notFound().build();
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<?> editVehicle(@PathVariable Long id, @RequestBody @Valid VehicleForm form) {
    
    Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
    if (optionalVehicle.isPresent()) {
      Vehicle vehicle = form.toVehicle(brandRepository);
      vehicle.setId(optionalVehicle.get().getId());
      
      vehicleRepository.save(vehicle);
      
      return ResponseEntity.ok().build();
    }
    
    return ResponseEntity.notFound().build();
  }
}

