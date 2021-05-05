package com.example.demo.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.demo.domain.ProdosUser;
import com.example.demo.service.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTAuthenticationService {

	private static final String SECRETKEY = Base64.getEncoder().encodeToString("SecretKey".getBytes());
	private static final String PREFIX = "Bearer";
	private static final String EMPTY = "";
	private static final long EXPIRATIONTIME = 864000000;
	private static final String AUTHORIZATION = "Authorization";
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	public String createToken(String username, List<ProdosUser> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("roles", roles);
		
		Long now = System.currentTimeMillis();
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(now))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS256, SECRETKEY)
				.compact();
	}
	
	public Authentication getAuthentication(HttpServletRequest request) {
		String token = resolveToken(request);
		
		if(token != null && validateToken(token)) {
			String userName = getUsername(token);
			
			if(userName != null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
				return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			}
		}
		return null;
	}
	
	private String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader(AUTHORIZATION);
		
		if(bearerToken != null && bearerToken.startsWith(PREFIX)) {
			return bearerToken.replace(PREFIX, EMPTY).trim();
		}
		return null;
	}
	
	private boolean validateToken(String token) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token);
			
			if(claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
			
		} catch (JwtException | IllegalArgumentException e) {
			throw new IllegalArgumentException("Expired or invalid JWT token");
		}
	}
	
	private String getUsername(String token) {
		return Jwts.parser()
				.setSigningKey(SECRETKEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
}
