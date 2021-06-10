package br.thoughtworks.acelera3.CarangoBom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.thoughtworks.acelera3.CarangoBom.models.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long>{
  Optional<Brand> findByName(String name);
}

