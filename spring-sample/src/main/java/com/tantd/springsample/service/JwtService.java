/**
 * 
 */
package com.tantd.springsample.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.tantd.springsample.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	private static final Log log = LogFactory.getLog(JwtService.class);

	public static final String USERNAME = "username";
	public static final String ROLES = "roles";
	public static final String SECRET_KEY = "25djuqrwhnjpbaha2jeq6prwtrw6xu26";
	public static final int EXPIRE_TIME = 86400000 * 7;

	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();
		claims.put(USERNAME, user.getUsername());
		claims.put(ROLES, user.getRoles());
		String compactJws = Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

		return compactJws;
	}

	private Claims getClaimsFromToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return claimsJws.getBody();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + EXPIRE_TIME);
	}

	private Date getExpirationDateFromToken(String token) {
		Date expiration = null;
		Claims claims = getClaimsFromToken(token);
		expiration = claims.getExpiration();
		return expiration;
	}

	public String getUsernameFromToken(String token) {
		String username = null;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.get(USERNAME, String.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return username;
	}

	public Set<String> getRolesFromToken(String token) {
		Set<String> roles = null;
		try {
			Claims claims = getClaimsFromToken(token);
			@SuppressWarnings("unchecked")
			List<String> list = claims.get(ROLES, List.class);
			roles = new HashSet<>(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return roles;
	}

	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token) {
		if (token == null || token.trim().length() == 0) {
			return false;
		}
		String username = getUsernameFromToken(token);

		if (username == null || username.isEmpty()) {
			return false;
		}
		if (isTokenExpired(token)) {
			return false;
		}
		return true;
	}
}
