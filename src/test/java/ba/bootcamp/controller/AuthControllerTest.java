package ba.bootcamp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import ba.bootcamp.repository.RoleRepository;
import ba.bootcamp.repository.UserRepository;
import ba.bootcamp.request.SignupRequest;
import ba.bootcamp.response.MessageResponse;

public class AuthControllerTest {

	@InjectMocks
	private AuthController authController;

	@Mock
	UserRepository userRepository;

	@Mock
	RoleRepository roleRepository;

	private SignupRequest signUpRequest;

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
		when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);
		ResponseEntity<?> response = authController.registerUser(signUpRequest);

		assertEquals(((MessageResponse) response.getBody()).getMessage(), "Error: Username is already taken!");

	}
	
	@Test
	public void registerUserExistingEmailTest() {
		when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
		when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);
		ResponseEntity<?> response = authController.registerUser(signUpRequest);
		
		assertEquals(((MessageResponse) response.getBody()).getMessage(), "Error: Email is already in use!");
	}

}
