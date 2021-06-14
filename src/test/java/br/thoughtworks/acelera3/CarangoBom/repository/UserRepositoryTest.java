package br.thoughtworks.acelera3.CarangoBom.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import br.thoughtworks.acelera3.CarangoBom.models.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void ShouldFindUserByUsername() {
		Optional<User> user = userRepository.findByUsername("teste");
		Assert.assertTrue(user.isPresent());
	}
	
	@Test
	public void ShouldNotFindUserByInexistingUsername() {
		Optional<User> user = userRepository.findByUsername("admin");
		Assert.assertFalse(user.isPresent());
	}
}
