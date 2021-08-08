package com.manabie.springbootmanabie.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manabie.springbootmanabie.config.ValidateToken;
import com.manabie.springbootmanabie.entity.Tasks;
import com.manabie.springbootmanabie.entity.Users;
import com.manabie.springbootmanabie.model.ResponseDTO;
import com.manabie.springbootmanabie.model.TaskDTO;
import com.manabie.springbootmanabie.service.TaskService;
import com.manabie.springbootmanabie.service.UserDetailsServiceImpl;

@RestController
@CrossOrigin
public class MainController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ValidateToken validateToken;

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam("user_id") String username, @RequestParam String password)
			throws Exception {

		authenticate(username, password);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		final String token = validateToken.generateToken(userDetails);
		return ResponseEntity.ok(new ResponseDTO(token));
	}

	@PostMapping("/tasks")
	public ResponseEntity<?> createTasks(@RequestBody TaskDTO taskDTO, Principal principal) throws Exception {

		String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		List<Tasks> tasks = taskService.getTasks(principal.getName(), currentDate);
		Users user = userDetailsService.getUser(principal.getName());
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: ");
		}
		if (tasks.size() >= user.getMaxTodo()) {
			return new ResponseEntity<>("Over limited tasks for per day: " + user.getMaxTodo(),
					HttpStatus.NOT_ACCEPTABLE);
		}
		return ResponseEntity.ok(taskService.createTask(taskDTO, principal.getName(), currentDate));
	}

	@GetMapping("/tasks")
	public ResponseEntity<?> getTasks(@RequestParam("created_date") String createDate, Principal principal)
			throws Exception {
		List<Tasks> tasks = taskService.getTasks(principal.getName(), createDate);
		return ResponseEntity.ok(tasks);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
