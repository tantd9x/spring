/**
 * 
 */
package com.tantd.springsample.controller;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tantd.springsample.entity.User;
import com.tantd.springsample.service.JwtService;
import com.tantd.springsample.service.UserService;

/**
 * @author tantd
 *
 */
@RestController
@RequestMapping("/dooor")
public class SampleController {

	private static final Logger log = LoggerFactory.getLogger(SampleController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/nfo.txt", produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String getVersion() {
		try {
			Resource resource = new ClassPathResource("nfo.txt");
			InputStream is = resource.getInputStream();
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer, "UTF8");
			return writer.toString();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return "No File nfo.txt";
		}
	}

	@Inject
	private UserService userService;
	@Inject
	private JwtService jwtService;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser() {
		return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable int id) {
		User user = userService.findById(id);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		return new ResponseEntity<>("Not Found User", HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/users/create", method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody User user) {
		if (userService.add(user)) {
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("User Existed!", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/users/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable int id) {
		userService.delete(id);
		return new ResponseEntity<>("Deleted!", HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		String result = "";
		HttpStatus httpStatus = null;
		try {
			User user = userService.authen(username, password);
			if (user != null) {
				result = jwtService.generateToken(user);
				httpStatus = HttpStatus.OK;
			} else {
				result = "Wrong username/password";
				httpStatus = HttpStatus.BAD_REQUEST;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result = "Server Error";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(result, httpStatus);
	}
}
