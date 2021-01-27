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

	// Kontoler koji se testira
	@InjectMocks
	private AuthController authController;

	// Proprema Mock objekta
	@Mock
	UserRepository userRepository;

	// Proprema Mock objekta
	@Mock
	RoleRepository roleRepository;

	private SignupRequest signUpRequest;

	// Priprema podataka prije izvrsavanja testova
	@SuppressWarnings("deprecation")
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
		// da bi se stvorio uslov za ulazak u prvi if statement
		when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);
		
		// Ovo se testira
		ResponseEntity<?> response = authController.registerUser(signUpRequest);

		// Ovo provjerava ispravnost testa
		assertEquals(((MessageResponse) response.getBody()).getMessage(), "Error: Username is already taken!");

	}
	
	@Test
	public void registerUserExistingEmailTest() {
		// Da bi se stvorio uslov da preskocimo prvi if statement
		when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(false);
		
		// Da bi se stvorio uslov za ulazak u drugi if statement
		when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);
		
		
		// Ovo se testira
		ResponseEntity<?> response = authController.registerUser(signUpRequest);
		
		// Ovo je provjera ispravnosti testa
		assertEquals(((MessageResponse) response.getBody()).getMessage(), "Error: Email is already in use!");
	}

}
