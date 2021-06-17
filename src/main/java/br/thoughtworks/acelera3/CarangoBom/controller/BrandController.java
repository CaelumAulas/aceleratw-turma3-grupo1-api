package br.thoughtworks.acelera3.CarangoBom.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import br.thoughtworks.acelera3.CarangoBom.controller.form.BrandForm;
import br.thoughtworks.acelera3.CarangoBom.dto.BrandDto;
import br.thoughtworks.acelera3.CarangoBom.dto.BrandListDto;
import br.thoughtworks.acelera3.CarangoBom.models.Brand;
import br.thoughtworks.acelera3.CarangoBom.repository.BrandRepository;

@RestController
@RequestMapping("/brand")
public class BrandController {
	
	@Autowired
	private BrandRepository brandRepository;
	
	@GetMapping("{id}")
	public ResponseEntity<BrandDto> findById(@PathVariable Long id){
		Optional<Brand> brand = brandRepository.findById(id);
		
		if(brand.isPresent()) {
			return ResponseEntity.ok(new BrandDto(brand.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public ResponseEntity<BrandListDto> getBrandList(@PageableDefault(sort="id", direction=Direction.ASC) Pageable pagination,
                              						 @RequestParam(required = false) String name){
		
		Page<Brand> brands = brandRepository.findByNameContains(name == null ? "" : name, pagination);
		
		return ResponseEntity.ok(new BrandListDto(brands));
	}
	
	@PostMapping
	public ResponseEntity<?> insertNewBrand(@RequestBody @Valid BrandForm brandForm, UriComponentsBuilder uriBuilder){
		Brand brand = brandForm.toBrand();
		
		try	{
			brandRepository.save(brand);
			
			URI uri = uriBuilder.path("/brand/{id}").buildAndExpand(brand.getId()).toUri();
		    return ResponseEntity.created(uri).body(new BrandDto(brand));
		}
		catch(DataIntegrityViolationException e) {
			return ResponseEntity.badRequest().body("Marca já existente.");
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id){
		Optional<Brand> brand = brandRepository.findById(id);
		
		if(brand.isPresent()) {
			brandRepository.deleteById(brand.get().getId());
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateBrand(@RequestBody @Valid BrandForm form, @PathVariable Long id){
		Optional<Brand> optionalBrand= brandRepository.findById(id);
	    if (optionalBrand.isPresent()) {
	      Brand brand = form.toBrand();
	      brand.setId(optionalBrand.get().getId());
	      
	      brandRepository.save(brand);
	      
	      return ResponseEntity.ok().build();
	    }
	    
	    return ResponseEntity.notFound().build();
	}

}
