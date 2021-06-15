package br.thoughtworks.acelera3.CarangoBom.controller;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Locale;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import br.thoughtworks.acelera3.CarangoBom.dto.BrandListDto;
import br.thoughtworks.acelera3.CarangoBom.models.Brand;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BrandControllerTests extends ControllerTests {

	@BeforeAll
	private static void createUserUri() throws URISyntaxException {
		createUri("/brand");
	}
	
	@Test
	public void ShouldCreateANewBrand() throws UnsupportedEncodingException, URISyntaxException, Exception {
		String name = "BMW";
	    String body = String.format(Locale.US, "{ \"name\":\"%s\" }", name);
	  
	    postMockWithToken(body, 201, getToken("teste", "123"));
	}
	
	@Test
	public void ShouldNotCreateANewBrandWithoutAuthorization() throws UnsupportedEncodingException, URISyntaxException, Exception {
		String name = "BMW";
	    String body = String.format(Locale.US, "{ \"name\":\"%s\" }", name);
	  
	    postMock(body, 403);
	}
	
	@Test
	public void ShouldNotCreateANewBrandWithWrongCredentials() throws UnsupportedEncodingException, URISyntaxException, Exception {
		String name = "BMW";
	    String body = String.format(Locale.US, "{ \"name\":\"%s\" }", name);
	  
	    postMockWithToken(body, 403, getToken("teste", "123456"));
	}
	
	@Test
	public void ShouldNotCreateANewBrandWithEmptyName() throws UnsupportedEncodingException, URISyntaxException, Exception {
		String name = "";
	    String body = String.format(Locale.US, "{ \"name\":\"%s\" }", name);
	  
	    postMockWithToken(body, 400, getToken("teste", "123"));
	}
	
	@Test
	public void ShouldNotCreateANewBrandWithNullName() throws UnsupportedEncodingException, URISyntaxException, Exception {
	    postMockWithToken("{}", 400, getToken("teste", "123"));
	}
	
	@Test
	public void ShouldNotCreateANewBrandWithExistingName() throws UnsupportedEncodingException, URISyntaxException, Exception {
		String name = "BMW";
	    String body = String.format(Locale.US, "{ \"name\":\"%s\" }", name);
	  
	    postMockWithToken(body, 201, getToken("teste", "123"));
	    postMockWithToken(body, 400, getToken("teste", "123"));
	}
	
	@Test
	public void shouldReturnBrandById() throws Exception {
	    Brand brand = mapFromJson(getMock("/1"), Brand.class);
	    Assert.assertEquals(new Long(1), brand.getId());
	    Assert.assertEquals("Ford", brand.getName());
    }
	
	@Test
	public void shouldNotReturnBrandWithInexistingId() throws Exception {
	    Brand brand = mapFromJson(getMock("/999"), Brand.class);
	    Assert.assertNull(brand);
    }
	
	@Test
	public void shouldFindBrandList() throws Exception {
		BrandListDto brands = mapFromJson(getMock("?page=0&size=20"), BrandListDto.class);
		Assert.assertEquals(2, brands.getContent().size());
		Assert.assertEquals(0, brands.getPage());
		Assert.assertEquals(1, brands.getTotalPages());
		Assert.assertEquals(20, brands.getPageSize());
		Assert.assertFalse(brands.getHasNext());
		Assert.assertFalse(brands.getHasPrevious());
	}
	
	@Test
	public void shouldFindBrandListByBrandName() throws Exception {
		BrandListDto brands = mapFromJson(getMock("?page=0&size=20&name=Ford"), BrandListDto.class);
		Assert.assertEquals(1, brands.getContent().size());
		Assert.assertEquals(0, brands.getPage());
		Assert.assertEquals(1, brands.getTotalPages());
		Assert.assertEquals(20, brands.getPageSize());
		Assert.assertFalse(brands.getHasNext());
		Assert.assertFalse(brands.getHasPrevious());
	}
	
	@Test 
	public void shouldDeleteABrandById() throws Exception{
		deleteMock(1l, 200, getToken("teste", "123"));
		Brand brand = mapFromJson(getMock("/1"), Brand.class);
		Assert.assertNull(brand);
	}
	
	@Test 
	public void shouldNotDeleteABrandWithInexistingId() throws Exception{
		deleteMock(999l, 404, getToken("teste", "123"));
	}
	
	@Test 
	public void shouldNotDeleteABrandWithWrongCredentials() throws Exception{
		deleteMock(1l, 403, getToken("teste", "123456"));
	}
	
	@Test 
	public void shouldEditAnExistingBrand() throws Exception{
		String brandName = "Alfa Romeo";
	    Brand brandBefore = mapFromJson(getMock("/1"), Brand.class);
	    Assert.assertNotEquals(brandName, brandBefore.getName());
	    
	    String body = String.format(Locale.US, "{ \"name\":\"%s\" }", brandName);
	    
	    putMock(1l, body, 200, getToken("teste", "123"));
	    
	    Brand brand = mapFromJson(getMock("/1"), Brand.class);
	    Assert.assertEquals(brandName, brand.getName());
	}
	
	@Test 
	public void shouldNotEditAnExistingBrandWithWrongCredentials() throws Exception{
		String brandName = "Alfa Romeo";
	    String body = String.format(Locale.US, "{ \"name\":\"%s\" }", brandName);
	    
	    putMock(1l, body, 403, getToken("teste", "123456"));
	}
	
	@Test 
	public void shouldNotEditAnExistingBrandWithNullBody() throws Exception{ 
	    putMock(1l, "", 400, getToken("teste", "123"));
	}
	
	@Test 
	public void shouldNotEditAnExistingBrandWithEmptyBody() throws Exception{
		String brandName = "";
	    String body = String.format(Locale.US, "{ \"name\":\"%s\" }", brandName);
	    
	    putMock(1l, body, 400, getToken("teste", "123"));
	}
}
