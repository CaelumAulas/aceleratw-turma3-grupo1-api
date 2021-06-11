package br.thoughtworks.acelera3.CarangoBom.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import br.thoughtworks.acelera3.CarangoBom.models.Brand;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
class BrandRepositoryTest {

	
	@Autowired
	private BrandRepository brandRepository;
	
	@Test
	public void ShouldFindBrandByName() {
		Optional<Brand> brand = brandRepository.findByName("Ford");
		Assert.assertTrue(brand.isPresent());
	}
	
	@Test
	public void ShouldNotFindBrandByInexistingName() {
		Optional<Brand> brand = brandRepository.findByName("Marca Inexistente");
		Assert.assertFalse(brand.isPresent());
	}

}
