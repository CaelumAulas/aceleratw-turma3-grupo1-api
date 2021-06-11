package br.thoughtworks.acelera3.CarangoBom.controller;

import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import br.thoughtworks.acelera3.CarangoBom.dto.UserListDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTests extends ControllerTests {
	
	@BeforeAll
	private static void createUserUri() throws URISyntaxException {
		createUri("user");
	}
	
	public void shouldFindUserList() throws Exception {
		UserListDto user = mapFromJson(getMock("?page=0&size=20"), UserListDto.class);
		Assert.assertEquals(1, user.getContent().size());
	    Assert.assertEquals(0, user.getPage());
	    Assert.assertEquals(1, user.getTotalPages());
	    Assert.assertEquals(20, user.getPageSize());
	    Assert.assertFalse(user.getHasNext());
	    Assert.assertFalse(user.getHasPrevious());
	}
	
}
