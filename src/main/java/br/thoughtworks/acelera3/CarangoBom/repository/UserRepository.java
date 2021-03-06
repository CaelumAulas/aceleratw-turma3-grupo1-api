package br.thoughtworks.acelera3.CarangoBom.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.thoughtworks.acelera3.CarangoBom.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUsername(String username);
	
	Page<User> findAll(Pageable pageable);

}
