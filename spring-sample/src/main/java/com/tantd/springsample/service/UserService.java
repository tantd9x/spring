/**
 * 
 */
package com.tantd.springsample.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.tantd.springsample.entity.User;
import com.tantd.springsample.entity.User.UserRole;

/**
 * @author tantd
 *
 */
@Service
public class UserService {

	private static List<User> users = new ArrayList<>();
	private static Map<Integer, User> mapUsers = new HashMap<>();

	static {
		User user1 = new User(1, "user", "user", UserRole.USER);
		User user2 = new User(2, "admin", "admin", UserRole.ADMIN);
		users.add(user1);
		users.add(user2);
		mapUsers.put(user1.getId(), user1);
		mapUsers.put(user2.getId(), user2);
	}

	public List<User> findAll() {
		return users;
	}

	public User findById(int id) {
		return mapUsers.get(id);
	}

	public User findByUsername(String username) {
		for (User u : users) {
			if (StringUtils.equals(username, u.getUsername())) {
				return u;
			}
		}
		return null;
	}

	public boolean add(User user) {
		for (User u : users) {
			if (user.getId() == u.getId() || StringUtils.equals(user.getUsername(), u.getUsername())) {
				return false;
			}
		}
		users.add(user);
		mapUsers.put(user.getId(), user);
		return true;
	}

	public void delete(int id) {
		users.removeIf(user -> user.getId() == id);
		mapUsers.remove(id);
	}

	public User authen(String username, String password) {
		for (User u : users) {
			if (StringUtils.equals(username, u.getUsername()) && StringUtils.equals(password, u.getPassword())) {
				return u;
			}
		}
		return null;
	}

}
