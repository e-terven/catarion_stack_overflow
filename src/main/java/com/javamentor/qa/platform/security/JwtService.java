package com.javamentor.qa.platform.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

	@Value("${jwt.secret}")
	private String jwtSecret;

	public boolean isTokenValid(String token, UserDetails userDetails) {
		String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = exctractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, claims -> claims.getExpiration());
	}

	public String extractUsername(String token) {
		return extractClaim(token, claims -> claims.getSubject());
	}

	public String generateToken(UserDetails userDetails) {
		return Jwts
			.builder()
			.setSubject(userDetails.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
			.signWith(getSignInKey())
			.compact();
	}

	private Claims exctractAllClaims(String token) {
			return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();

	}

	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
