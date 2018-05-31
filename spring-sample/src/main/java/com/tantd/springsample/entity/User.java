/**
 * 
 */
package com.tantd.springsample.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author tantd
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "roles", "authorities" })
@Data
public class User {

	public static enum UserRole {
		USER,
		ADMIN
	}

	private int id;
	private String username;
	private String password;
	private Set<String> roles = new HashSet<>();

	public User() {

	}

	public User(int id, String username, String password, UserRole... roles) {
		this.id = id;
		this.username = username;
		this.password = password;
		for (UserRole role : roles) {
			this.roles.add(role.name());
		}
	}

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

}
