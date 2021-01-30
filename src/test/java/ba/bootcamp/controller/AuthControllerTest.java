package ba.bootcamp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import ba.bootcamp.repository.RoleRepository;
import ba.bootcamp.repository.UserRepository;
import ba.bootcamp.request.LoginRequest;
import ba.bootcamp.request.SignupRequest;
import ba.bootcamp.response.JwtResponse;
import ba.bootcamp.response.MessageResponse;
import ba.bootcamp.security.services.UserDetailsImpl;
import ba.bootcamp.security.services.UserDetailsServiceImpl;

public class AuthControllerTest {

	// Tested controller
	@InjectMocks
	private AuthController authController;
	
	@InjectMocks
	AuthenticationManager authenticationManager;

	// Preparation of Mock object
	@Mock
	UserRepository userRepository;

	// Preparation of Mock object
	@Mock
	RoleRepository roleRepository;

	private SignupRequest signUpRequest;
	
	private LoginRequest loginRequest;

	// Preparation of test data
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		signUpRequest = new SignupRequest();
		signUpRequest.setUsername("testuser");
		signUpRequest.setPassword("testuser");
		signUpRequest.setEmail("testuser@test.com");
	}

	@Test
	public void registerUserExistingUsernameTest() {
		// to create condition for entering in first if statement
		when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);
		
		// Testing registerUser method
		ResponseEntity<?> response = authController.registerUser(signUpRequest);

		// Test validity check
		assertEquals(((MessageResponse) response.getBody()).getMessage(), "Error: Username is already taken!");
	}
	
	@Test
	public void registerUserExistingEmailTest() {
		// to create condition for skip first if statement
		when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
		
		// to create condition for entering in second if statement
		when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);
		
		// Testing registerUser method
		ResponseEntity<?> response = authController.registerUser(signUpRequest);
		
		// Test validity check
		assertEquals(((MessageResponse) response.getBody()).getMessage(), "Error: Email is already in use!");
	}
	
	

}
