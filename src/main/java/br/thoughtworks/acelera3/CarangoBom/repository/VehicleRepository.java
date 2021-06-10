package br.thoughtworks.acelera3.CarangoBom.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.thoughtworks.acelera3.CarangoBom.models.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	Optional<Vehicle> findById(Long id);

	Page<Vehicle> findAllByBrandNameContainsAndModelContainsAndYearBetweenAndPriceBetween(String brand, String model, int minYear, int maxYear, double minPrice, double maxPrice, Pageable pagination);
}
