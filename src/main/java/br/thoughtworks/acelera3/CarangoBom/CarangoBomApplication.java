package br.thoughtworks.acelera3.CarangoBom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableSpringDataWebSupport
public class CarangoBomApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarangoBomApplication.class, args);
	}

}
