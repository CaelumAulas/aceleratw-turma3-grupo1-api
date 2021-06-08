package br.thoughtworks.acelera3.CarangoBom.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import br.thoughtworks.acelera3.CarangoBom.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenService {

	@Value("${carangobom.jwt.secret}")
	private String secret;
	
	@Value("${carangobom.jwt.expiration}")
	private String expiration;
	
	public String generateToken(Authentication authentication) {
		
		User user = (User)authentication.getPrincipal();		
		Date date = new Date();
		
		return Jwts.builder()
				.setId("Carango-Bom")
				.setSubject(user.getId().toString())
				.setIssuedAt(date)
				.setExpiration(new Date(date.getTime()+Long.parseLong(expiration)))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Long getUserID(String token) {
		
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		
		return Long.parseLong(claims.getSubject());
	}
}
