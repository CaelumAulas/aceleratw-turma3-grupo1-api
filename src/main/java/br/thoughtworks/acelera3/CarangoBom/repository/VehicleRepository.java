package br.thoughtworks.acelera3.CarangoBom.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.thoughtworks.acelera3.CarangoBom.models.Brand;
import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	Optional<Vehicle> findById(Long id);

	List<Vehicle> findAllByYear(int year);

	List<Vehicle> findAllByBrand(Brand brand);

	Page<Vehicle> findAllByBrandName(String string, Pageable pagination);

	List<Vehicle> findAllByModel(String string);

	List<Vehicle> findByPriceBetween(double min, double max);

}
