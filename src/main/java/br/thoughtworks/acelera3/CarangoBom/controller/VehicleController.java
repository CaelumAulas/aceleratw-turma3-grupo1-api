package br.thoughtworks.acelera3.CarangoBom.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.thoughtworks.acelera3.CarangoBom.dto.VehicleDto;
import br.thoughtworks.acelera3.CarangoBom.dto.VehicleListDto;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;
import br.thoughtworks.acelera3.CarangoBom.repository.VehicleRepository;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

	@Autowired
	private VehicleRepository vehicleRepository;
	
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
														  @RequestParam(required = false) String brand)
	{
		Page<Vehicle> vehicles = vehicleRepository.findAllByBrandName(brand, pagination);
		return ResponseEntity.ok(new VehicleListDto(vehicles));
	}
	
}
