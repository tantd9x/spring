/**
 * 
 */
package com.tantd.test;

import java.util.Set;

import com.tantd.springsample.entity.User;
import com.tantd.springsample.entity.User.UserRole;
import com.tantd.springsample.service.JwtService;

/**
 * @author tantd
 *
 */
public class TestJwt {

	public static void main(String[] args) {
		User user = new User(1, "user", "user", UserRole.USER);
		JwtService jwtService = new JwtService();
		String token = jwtService.generateToken(user);
		System.out.println(token);
		String usernameFromToken = jwtService.getUsernameFromToken(token);
		Set<String> rolesFromToken = jwtService.getRolesFromToken(token);
		System.out.println(usernameFromToken + " " + rolesFromToken);
	}
}
