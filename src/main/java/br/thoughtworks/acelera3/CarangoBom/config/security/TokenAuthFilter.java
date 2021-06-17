package br.thoughtworks.acelera3.CarangoBom.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.thoughtworks.acelera3.CarangoBom.models.User;
import br.thoughtworks.acelera3.CarangoBom.repository.UserRepository;

public class TokenAuthFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	
	private UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recoverToken(request);
		if (tokenService.isTokenValid(token)) {
			authenticateClient(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	private String recoverToken(HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;			
		}
		
		return token.replace("Bearer ", "");
	}
	
	private void authenticateClient(String token) {
		Long userId = tokenService.getUserID(token);
		User user = userRepository.findById(userId).get();
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
	

	public TokenAuthFilter(TokenService tokenService, UserRepository userRepository) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
	}
	
	
	
}
